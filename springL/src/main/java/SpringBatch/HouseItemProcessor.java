package SpringBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class HouseItemProcessor implements ItemProcessor<House, House> {

  private static final Logger log = LoggerFactory.getLogger(HouseItemProcessor.class);

  @Override
  public House process(final House House) throws Exception {
    final Double income = Double.valueOf(House.getIncome());
    final String pet = House.getPet().toUpperCase();

    final House transformedHouse = new House(income, pet);

    log.info("Converting (" + House + ") into (" + transformedHouse + ")");

    return transformedHouse;
  }

}