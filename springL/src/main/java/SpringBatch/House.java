package SpringBatch;

public class House {
  private Double income;
  private String pet;

  public House() {
  }

  public House(Double income, String pet) {
    this.income = income;
    this.pet = pet;
  }

  public Double getIncome() {
    return income;
  }

  public void setIncome(Double income) {
    this.income = income;
  }

  public String getPet() {
    return pet;
  }

  public void setPet(String pet) {
    this.pet = pet;
  }

  @Override
  public String toString() {
    return "House{" +
            "income=" + income +
            ", pet='" + pet + '\'' +
            '}';
  }
}
