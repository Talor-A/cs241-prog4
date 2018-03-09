public class City {
	String code;
	String name;
	int number;
	int population;
	int elevation;

	public City(String code, String name, int number, int population, int elevation) {
		this.name = name;
		this.code = code;
		this.number = number;
		this.population = population;
		this.elevation = elevation;
	}
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
	public int getNumber() {
		return number;
	}
	public String toString() {
		return code + " "
		       + number + " "
		       + name + " "
		       + population + " "
		       + elevation;
	}

}