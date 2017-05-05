package com.educonnect.server.db;

import java.util.Random;
import java.util.UUID;

public class NameGenerator {
	
	private static String[][] MCU = { { "Captain", "America" },
									  { "Iron", "Man" },
									  { "War", "Machine" },
									  { "The", "Hulk" },
									  { "Erik", "Selvig" },
									  { "J.A.R", "V.I.S" },
									  { "Pepper", "Potts" } };
									  
	
	private static String[][] JL = { { "Bruce", "Wayne" },
									 { "Clark", "Kent" },
									 { "Lois", "Lane" },
									 { "Harley", "Quinn" },
									 { "Barry", "Allen" },
									 { "Lex", "Luthor" } };
	
	private static String[][] HP = { { "Harry", "Potter" },
									 { "Ronald", "Weasley" },
									 { "Hermione", "Granger" },
									 { "Neville", "Longbottom" },
									 { "Albus", "Dumbledore" },
									 { "Severus", "Snape" },
									 { "Draco", "Malfoy" },
									 { "Bellatrix", "Lestrange" },
									 { "Severus", "Snape" } };
	
	private static final String[][][] multiverse = { HP, JL, MCU };
	
	public static String[] generateCompleteName() {
		Random random = new Random( UUID.randomUUID().getMostSignificantBits() );
		
		String[][] universe = multiverse[ random.nextInt( multiverse.length ) ];
		String[] name = universe[ random.nextInt( universe.length ) ];
		return name;
	}

}
