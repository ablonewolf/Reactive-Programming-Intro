package org.ablonewolf.common;

import java.util.HashMap;
import java.util.Map;

/**
 * The CountryData class provides a mapping between countries and their respective continents.
 * It includes a static map containing predefined country-continent pairs.
 * This is a utility class that acts as a centralized data source for country-continent relationships.
 */
public class CountryData {
	private static final Map<String, String> COUNTRY_CONTINENT_MAP = new HashMap<>();

	static {
		// North America
		COUNTRY_CONTINENT_MAP.put("United States", "North America");
		COUNTRY_CONTINENT_MAP.put("Canada", "North America");
		COUNTRY_CONTINENT_MAP.put("Mexico", "North America");
		COUNTRY_CONTINENT_MAP.put("Guatemala", "North America");
		COUNTRY_CONTINENT_MAP.put("Honduras", "North America");
		COUNTRY_CONTINENT_MAP.put("El Salvador", "North America");
		COUNTRY_CONTINENT_MAP.put("Nicaragua", "North America");
		COUNTRY_CONTINENT_MAP.put("Costa Rica", "North America");
		COUNTRY_CONTINENT_MAP.put("Panama", "North America");
		COUNTRY_CONTINENT_MAP.put("Belize", "North America");

		// Caribbean (part of North America)
		COUNTRY_CONTINENT_MAP.put("Cuba", "North America");
		COUNTRY_CONTINENT_MAP.put("Jamaica", "North America");
		COUNTRY_CONTINENT_MAP.put("Haiti", "North America");
		COUNTRY_CONTINENT_MAP.put("Dominican Republic", "North America");
		COUNTRY_CONTINENT_MAP.put("Bahamas", "North America");
		COUNTRY_CONTINENT_MAP.put("Barbados", "North America");
		COUNTRY_CONTINENT_MAP.put("Trinidad and Tobago", "North America");

		// South America
		COUNTRY_CONTINENT_MAP.put("Brazil", "South America");
		COUNTRY_CONTINENT_MAP.put("Argentina", "South America");
		COUNTRY_CONTINENT_MAP.put("Peru", "South America");
		COUNTRY_CONTINENT_MAP.put("Colombia", "South America");
		COUNTRY_CONTINENT_MAP.put("Venezuela", "South America");
		COUNTRY_CONTINENT_MAP.put("Chile", "South America");
		COUNTRY_CONTINENT_MAP.put("Ecuador", "South America");
		COUNTRY_CONTINENT_MAP.put("Bolivia", "South America");
		COUNTRY_CONTINENT_MAP.put("Paraguay", "South America");
		COUNTRY_CONTINENT_MAP.put("Uruguay", "South America");
		COUNTRY_CONTINENT_MAP.put("Guyana", "South America");
		COUNTRY_CONTINENT_MAP.put("Suriname", "South America");

		// Europe
		COUNTRY_CONTINENT_MAP.put("United Kingdom", "Europe");
		COUNTRY_CONTINENT_MAP.put("France", "Europe");
		COUNTRY_CONTINENT_MAP.put("Germany", "Europe");
		COUNTRY_CONTINENT_MAP.put("Italy", "Europe");
		COUNTRY_CONTINENT_MAP.put("Spain", "Europe");
		COUNTRY_CONTINENT_MAP.put("Portugal", "Europe");
		COUNTRY_CONTINENT_MAP.put("Ireland", "Europe");
		COUNTRY_CONTINENT_MAP.put("Netherlands", "Europe");
		COUNTRY_CONTINENT_MAP.put("Belgium", "Europe");
		COUNTRY_CONTINENT_MAP.put("Switzerland", "Europe");
		COUNTRY_CONTINENT_MAP.put("Austria", "Europe");
		COUNTRY_CONTINENT_MAP.put("Greece", "Europe");
		COUNTRY_CONTINENT_MAP.put("Sweden", "Europe");
		COUNTRY_CONTINENT_MAP.put("Norway", "Europe");
		COUNTRY_CONTINENT_MAP.put("Denmark", "Europe");
		COUNTRY_CONTINENT_MAP.put("Finland", "Europe");
		COUNTRY_CONTINENT_MAP.put("Poland", "Europe");
		COUNTRY_CONTINENT_MAP.put("Romania", "Europe");
		COUNTRY_CONTINENT_MAP.put("Bulgaria", "Europe");
		COUNTRY_CONTINENT_MAP.put("Hungary", "Europe");
		COUNTRY_CONTINENT_MAP.put("Czech Republic", "Europe");
		COUNTRY_CONTINENT_MAP.put("Slovakia", "Europe");
		COUNTRY_CONTINENT_MAP.put("Croatia", "Europe");
		COUNTRY_CONTINENT_MAP.put("Serbia", "Europe");
		COUNTRY_CONTINENT_MAP.put("Slovenia", "Europe");
		COUNTRY_CONTINENT_MAP.put("Bosnia and Herzegovina", "Europe");
		COUNTRY_CONTINENT_MAP.put("Macedonia", "Europe");
		COUNTRY_CONTINENT_MAP.put("Montenegro", "Europe");
		COUNTRY_CONTINENT_MAP.put("Albania", "Europe");
		COUNTRY_CONTINENT_MAP.put("Lithuania", "Europe");
		COUNTRY_CONTINENT_MAP.put("Latvia", "Europe");
		COUNTRY_CONTINENT_MAP.put("Estonia", "Europe");
		COUNTRY_CONTINENT_MAP.put("Belarus", "Europe");
		COUNTRY_CONTINENT_MAP.put("Ukraine", "Europe");
		COUNTRY_CONTINENT_MAP.put("Moldova", "Europe");

		// Asia
		COUNTRY_CONTINENT_MAP.put("China", "Asia");
		COUNTRY_CONTINENT_MAP.put("Japan", "Asia");
		COUNTRY_CONTINENT_MAP.put("South Korea", "Asia");
		COUNTRY_CONTINENT_MAP.put("North Korea", "Asia");
		COUNTRY_CONTINENT_MAP.put("Vietnam", "Asia");
		COUNTRY_CONTINENT_MAP.put("Cambodia", "Asia");
		COUNTRY_CONTINENT_MAP.put("Thailand", "Asia");
		COUNTRY_CONTINENT_MAP.put("Myanmar", "Asia");
		COUNTRY_CONTINENT_MAP.put("Laos", "Asia");
		COUNTRY_CONTINENT_MAP.put("Malaysia", "Asia");
		COUNTRY_CONTINENT_MAP.put("Singapore", "Asia");
		COUNTRY_CONTINENT_MAP.put("Indonesia", "Asia");
		COUNTRY_CONTINENT_MAP.put("Philippines", "Asia");
		COUNTRY_CONTINENT_MAP.put("India", "Asia");
		COUNTRY_CONTINENT_MAP.put("Pakistan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Bangladesh", "Asia");
		COUNTRY_CONTINENT_MAP.put("Nepal", "Asia");
		COUNTRY_CONTINENT_MAP.put("Bhutan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Sri Lanka", "Asia");
		COUNTRY_CONTINENT_MAP.put("Afghanistan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Iran", "Asia");
		COUNTRY_CONTINENT_MAP.put("Iraq", "Asia");
		COUNTRY_CONTINENT_MAP.put("Saudi Arabia", "Asia");
		COUNTRY_CONTINENT_MAP.put("Yemen", "Asia");
		COUNTRY_CONTINENT_MAP.put("Oman", "Asia");
		COUNTRY_CONTINENT_MAP.put("United Arab Emirates", "Asia");
		COUNTRY_CONTINENT_MAP.put("Qatar", "Asia");
		COUNTRY_CONTINENT_MAP.put("Bahrain", "Asia");
		COUNTRY_CONTINENT_MAP.put("Kuwait", "Asia");
		COUNTRY_CONTINENT_MAP.put("Jordan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Lebanon", "Asia");
		COUNTRY_CONTINENT_MAP.put("Syria", "Asia");
		COUNTRY_CONTINENT_MAP.put("Israel", "Asia");
		COUNTRY_CONTINENT_MAP.put("Turkey", "Asia");
		COUNTRY_CONTINENT_MAP.put("Azerbaijan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Georgia", "Asia");
		COUNTRY_CONTINENT_MAP.put("Armenia", "Asia");
		COUNTRY_CONTINENT_MAP.put("Kazakhstan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Uzbekistan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Turkmenistan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Kyrgyzstan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Tajikistan", "Asia");
		COUNTRY_CONTINENT_MAP.put("Mongolia", "Asia");

		// Africa
		COUNTRY_CONTINENT_MAP.put("Egypt", "Africa");
		COUNTRY_CONTINENT_MAP.put("Libya", "Africa");
		COUNTRY_CONTINENT_MAP.put("Tunisia", "Africa");
		COUNTRY_CONTINENT_MAP.put("Algeria", "Africa");
		COUNTRY_CONTINENT_MAP.put("Morocco", "Africa");
		COUNTRY_CONTINENT_MAP.put("Sudan", "Africa");
		COUNTRY_CONTINENT_MAP.put("South Sudan", "Africa");
		COUNTRY_CONTINENT_MAP.put("Ethiopia", "Africa");
		COUNTRY_CONTINENT_MAP.put("Eritrea", "Africa");
		COUNTRY_CONTINENT_MAP.put("Somalia", "Africa");
		COUNTRY_CONTINENT_MAP.put("Djibouti", "Africa");
		COUNTRY_CONTINENT_MAP.put("Kenya", "Africa");
		COUNTRY_CONTINENT_MAP.put("Uganda", "Africa");
		COUNTRY_CONTINENT_MAP.put("Tanzania", "Africa");
		COUNTRY_CONTINENT_MAP.put("Rwanda", "Africa");
		COUNTRY_CONTINENT_MAP.put("Burundi", "Africa");
		COUNTRY_CONTINENT_MAP.put("Democratic Republic of the Congo", "Africa");
		COUNTRY_CONTINENT_MAP.put("Republic of the Congo", "Africa");
		COUNTRY_CONTINENT_MAP.put("Cameroon", "Africa");
		COUNTRY_CONTINENT_MAP.put("Nigeria", "Africa");
		COUNTRY_CONTINENT_MAP.put("Ghana", "Africa");
		COUNTRY_CONTINENT_MAP.put("Ivory Coast", "Africa");
		COUNTRY_CONTINENT_MAP.put("Senegal", "Africa");
		COUNTRY_CONTINENT_MAP.put("Mali", "Africa");
		COUNTRY_CONTINENT_MAP.put("Burkina Faso", "Africa");
		COUNTRY_CONTINENT_MAP.put("Niger", "Africa");
		COUNTRY_CONTINENT_MAP.put("Chad", "Africa");
		COUNTRY_CONTINENT_MAP.put("Sierra Leone", "Africa");
		COUNTRY_CONTINENT_MAP.put("Liberia", "Africa");
		COUNTRY_CONTINENT_MAP.put("Guinea", "Africa");
		COUNTRY_CONTINENT_MAP.put("Guinea-Bissau", "Africa");
		COUNTRY_CONTINENT_MAP.put("Gambia", "Africa");
		COUNTRY_CONTINENT_MAP.put("Mauritania", "Africa");
		COUNTRY_CONTINENT_MAP.put("Angola", "Africa");
		COUNTRY_CONTINENT_MAP.put("Zambia", "Africa");
		COUNTRY_CONTINENT_MAP.put("Zimbabwe", "Africa");
		COUNTRY_CONTINENT_MAP.put("Mozambique", "Africa");
		COUNTRY_CONTINENT_MAP.put("Malawi", "Africa");
		COUNTRY_CONTINENT_MAP.put("Madagascar", "Africa");
		COUNTRY_CONTINENT_MAP.put("South Africa", "Africa");
		COUNTRY_CONTINENT_MAP.put("Namibia", "Africa");
		COUNTRY_CONTINENT_MAP.put("Botswana", "Africa");
		COUNTRY_CONTINENT_MAP.put("Lesotho", "Africa");
		COUNTRY_CONTINENT_MAP.put("Swaziland", "Africa");

		// Oceania
		COUNTRY_CONTINENT_MAP.put("Australia", "Oceania");
		COUNTRY_CONTINENT_MAP.put("New Zealand", "Oceania");
		COUNTRY_CONTINENT_MAP.put("Papua New Guinea", "Oceania");
		COUNTRY_CONTINENT_MAP.put("Fiji", "Oceania");
		COUNTRY_CONTINENT_MAP.put("Solomon Islands", "Oceania");
		COUNTRY_CONTINENT_MAP.put("Vanuatu", "Oceania");
		COUNTRY_CONTINENT_MAP.put("New Caledonia", "Oceania");
		COUNTRY_CONTINENT_MAP.put("Samoa", "Oceania");
		COUNTRY_CONTINENT_MAP.put("Tonga", "Oceania");
	}

	/**
	 * Returns the continent for a given country.
	 *
	 * @param country The name of the country
	 * @return The continent name, or "Unknown" if the country is not found
	 */
	public static String getContinentForCountry(String country) {
		return COUNTRY_CONTINENT_MAP.getOrDefault(country, "Unknown");
	}
}
