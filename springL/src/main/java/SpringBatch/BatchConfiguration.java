package SpringBatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.support.DatabaseType;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@EnableBatchProcessing //adds many critical beans that support jobs
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    // tag::readerwriterprocessor[]
    // reader() creates an item reader to read csv line by line and parse it into your bean / in this case Person
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }
    // processor() create an instance for the processing
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }
    // write(DataSource) this is the writer that is  aimed at a JDBC , gets a copy of the dataSource. It includes the SQL to insert a single Person driven by Java bean properties.
    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public FlatFileItemReader<House> houseReader() {
        return new FlatFileItemReaderBuilder<House>()
                .name("houseItemReader")
                .resource(new ClassPathResource("sample-data2.csv"))
                .delimited()
                .names(new String[]{"income", "pet"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<House>() {{
                    setTargetType(House.class);
                }})
                .build();
    }

    @Bean
    public HouseItemProcessor houseProcessor() {
        return new HouseItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<House> houseWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<House>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO house (income, pet) VALUES (:income, :pet)")
                .dataSource(dataSource)
                .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]


    @Bean
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(step1(asyncTaskExecutor(), writer(dataSource)))
                .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(step2(asyncTaskExecutor(), houseWriter(dataSource)))
                .build();
    }

    @Bean
    public TaskExecutor asyncTaskExecutor(){
      return new SimpleAsyncTaskExecutor("spring_batch");
    }


    @Bean
    //   public Step step1(JdbcBatchItemWriter<Person> writer) {
    public Step step1(TaskExecutor asyncTaskExecutor, JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")
                // <Person,Person> because it’s a generic method; represents input and output types of each "chunk" of processing, and lines up with ItemReader<Person> and ItemWriter<Person>.
                .<Person, Person> chunk(10) // how many records to be process at a time
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .taskExecutor(asyncTaskExecutor)
                .build();
    }

    @Bean
    public Step step2(TaskExecutor asyncTaskExecutor, JdbcBatchItemWriter<House> houseWriter) {
        return stepBuilderFactory.get("step2")
                // <Person,Person> because it’s a generic method; represents input and output types of each "chunk" of processing, and lines up with ItemReader<Person> and ItemWriter<Person>.
                .<House, House> chunk(10) // how many records to be process at a time
                .reader(houseReader())
                .processor(houseProcessor())
                .writer(houseWriter)
                .taskExecutor(asyncTaskExecutor)
                .build();
    }

    @Bean
    public Job importJob(JobCompletionNotificationListener listener, Step step1, Step step2) {
        return jobBuilderFactory.get("importJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(splitFlow(listener, step1, step2))
//                .end()
                .build()
                .build();
    }

    @Bean
    public Flow splitFlow(JobCompletionNotificationListener listener, Step step1, Step step2) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(asyncTaskExecutor())
                .add(flow1(), flow2())
                .build();
    }
    // end::jobstep[]
}