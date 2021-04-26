//Emmy Eriksson.
abstract public class Valuables {

	private String name;

	public Valuables(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract double getValue();

	public double getRealValue() {
		return getValue() * 1.25;
	}

	public String toString() {
		return name + " " + "har värde: " + getRealValue() + "\n";
	}
}

class Jewellery extends Valuables {

	private int numberOfStones;
	private String type;

	public Jewellery(String name, int numberOfStones, String type) {
		super(name);
		this.numberOfStones = numberOfStones;
		this.type = type;
	}

	public int getNumberOfStones() {
		return numberOfStones;
	}

	public String getType() {
		return type;
	}

	public double getValue() {
		if (type == " av guld") {
			return numberOfStones * 500 + 2000;
		} else {
			return numberOfStones * 500 + 700;
		}
	}

	public String toString() {

		return super.toString() + "antal stenar: " + getNumberOfStones() + getType() + "\n";
	}

}

class Stock extends Valuables {

	private int quantity;
	private int stockPrice;

	public Stock(String name, int quantity, int stockPrice) {
		super(name);
		this.quantity = quantity;
		this.stockPrice = stockPrice;
	}

	public int getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice() {
		stockPrice = 0;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getValue() {
		return quantity * stockPrice;
	}

	public String toString() {
		return super.toString() + " har antal: " + getQuantity() + " och kurs: " + getStockPrice() + "\n";
	}

}

class Device extends Valuables {
	private int costPrice;
	private int condition;

	public Device(String name, int costPrice, int condition) {
		super(name);
		this.costPrice = costPrice;
		this.condition = condition;
	}

	public int getCondition() {
		return condition;
	}

	public int getCostPrice() {
		return costPrice;
	}

	public double getValue() {
		return (costPrice * condition) / 10;
	}

	public String toString() {
		return super.toString() + " har inköpspris: " + getCostPrice() + " och slitage: " + getCondition() + "\n";
	}

}
