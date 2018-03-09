// package GraphPackage;
// Java Program to illustrate reading from Text File
// using Scanner Class
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import GraphPackage.*;
public class GraphDriver {
	public static void main(String[] args) throws Exception {
		Graph<String> myGraph = new Graph<String>();
		HashMap<String, City> cityList = new HashMap<String, City>();
		HashMap<Integer, String> numToCity = new HashMap<Integer, String>();

		File cityFile = new File("city.dat");
		File roadFile = new File("road.dat");
		Scanner cities = new Scanner(cityFile);
		Scanner roads = new Scanner(roadFile);

		while (cities.hasNextLine() && cities.hasNext()) {
			int cityNum = cities.nextInt();
			String cityCode = cities.next();
			String cityName = cities.next();
			while (!cities.hasNextInt()) {
				cityName += (" " + cities.next());
			}
			int population = cities.nextInt();
			int elevation = cities.nextInt();

			City city = new City(
			    cityCode,
			    cityName,
			    cityNum,
			    population,
			    elevation
			);
			System.out.println(city);
			myGraph.addVertex(cityCode);
			cityList.put(cityCode, city);
			numToCity.put(cityNum, cityCode);
		}
		while (roads.hasNextLine() && roads.hasNext()) {
			int start = roads.nextInt();
			int end = roads.nextInt();
			int distance = roads.nextInt();

			String startCityCode = numToCity.get(start);
			String endCityCode = numToCity.get(end);

			// System.out.println("road: "+startCityCode+"=>"+endCityCode+":"+distance);
			myGraph.addEdge(startCityCode, endCityCode, distance);
		}


		Scanner kb = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			System.out.println("Loaded map.");
			System.out.println(myGraph.getNumberOfVertices() + " cities");
			System.out.println(myGraph.getNumberOfEdges() + " roads");

			System.out.print("Command? ");
			String input = kb.next().toUpperCase();
			if (input.equals("Q")) {
				System.out.print("City Code: ");
				String cityCode = kb.next().toUpperCase();
				System.out.println(cityList.get(cityCode));
			} else if (input.equals("D")) {
				System.out.print("City Codes: ");
				String start = kb.next().toUpperCase();
				String end = kb.next().toUpperCase();
				double dist =  myGraph.shortestPath(start, end);

				System.out.print("The minimum distance between ");
				System.out.print( cityList.get(start).getName() + " and " + cityList.get(end).getName());
				System.out.print(" is " + dist);
				String[] path = myGraph.pathTo(end).split(" ");
				for (int i = 0; i < path.length; i++) {
					path[i] = cityList.get(path[i]).getName();
				}
				System.out.println(" through the route: " + String.join(", ", path));
			} else if (input.equals("I")) {
				System.out.print("City codes and distance: ");
				String startCityCode = kb.next().toUpperCase();
				String endCityCode = kb.next().toUpperCase();
				double distance =  kb.nextInt();
				boolean success = myGraph.addEdge(startCityCode, endCityCode, distance);
				if (success) {
					System.out.print("You have inserted a road from ");
					System.out.print(cityList.get(startCityCode).getName());
					System.out.print(" and ");
					System.out.print(cityList.get(endCityCode).getName());
					System.out.print(" with distance ");
					System.out.println(distance);
				} else {
					System.out.println("Failed to add road.");
				}


			} else if (input.equals("R")) {
				System.out.print("City codes: ");
				String startCityCode = kb.next().toUpperCase();
				String endCityCode = kb.next().toUpperCase();
				boolean success = myGraph.removeEdge(startCityCode, endCityCode);
				if (success) {
					System.out.println("removed road between ");
					System.out.print(cityList.get(startCityCode).getName());
					System.out.print(" and ");
					System.out.print(cityList.get(endCityCode).getName());
				} else{
					System.out.println("there is no road between ");
					System.out.print(cityList.get(startCityCode).getName());
					System.out.print(" and ");
					System.out.print(cityList.get(endCityCode).getName());
				
				}
			} else if (input.equals("E")) {
				done = true;
			} else {
				System.out.println("Q Query the city information by entering the city code.");
				System.out.println("D Find the minimum distance between two cities.");
				System.out.println("I Insert a road by entering two city codes and distance.");
				System.out.println("R Remove an existing road by entering two city codes.");
				System.out.println("H Display this message.");
				System.out.println("E Exit.");
			}
		}
	}

}