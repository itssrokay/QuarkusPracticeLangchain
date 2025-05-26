package org.acme.tools.travel;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SuggestDestinationsTool {

    @Tool("Suggest travel destinations based on theme, budget, and duration. Provides personalized destination recommendations.")
    public String suggestDestinations(@P("Travel theme (adventure, relaxation, culture, nature, city, beach)") String theme,
                                    @P("Budget range (budget, mid-range, luxury)") String budget,
                                    @P("Trip duration in days") int duration) {
        System.out.println("=== SUGGEST DESTINATIONS TOOL CALLED ===");
        System.out.println("Theme: " + theme);
        System.out.println("Budget: " + budget);
        System.out.println("Duration: " + duration + " days");
        
        String suggestions = generateDestinationSuggestions(theme, budget, duration);
        
        // Ensure we never return null or empty
        if (suggestions == null || suggestions.trim().isEmpty()) {
            suggestions = "Error: Unable to generate destination suggestions. Please try again with valid theme, budget, and duration.";
        }
        
        System.out.println("Destination Suggestions: " + suggestions);
        System.out.println("=== END SUGGEST DESTINATIONS TOOL ===");
        
        return suggestions;
    }
    
    private String generateDestinationSuggestions(String theme, String budget, int duration) {
        // Ensure parameters are not null or empty
        if (theme == null || theme.trim().isEmpty()) {
            return "Error: Travel theme not specified. Please provide a theme like adventure, relaxation, culture, etc.";
        }
        if (budget == null || budget.trim().isEmpty()) {
            return "Error: Budget not specified. Please provide budget range like budget, mid-range, or luxury.";
        }
        if (duration <= 0) {
            return "Error: Invalid duration. Please provide a positive number of days.";
        }
        
        StringBuilder suggestions = new StringBuilder();
        suggestions.append(String.format("Destination suggestions for %s travel (%s budget, %d days):\n\n", 
            theme.trim(), budget.trim(), duration));
        
        switch (theme.toLowerCase().trim()) {
            case "adventure" -> {
                if (budget.equals("budget")) {
                    suggestions.append("1. Nepal - Trekking in Himalayas ($30-50/day)\n");
                    suggestions.append("2. Guatemala - Volcano hiking and Mayan ruins ($25-40/day)\n");
                    suggestions.append("3. Vietnam - Motorbike tours and cave exploration ($20-35/day)\n");
                } else if (budget.equals("luxury")) {
                    suggestions.append("1. New Zealand - Helicopter tours and luxury lodges ($200-400/day)\n");
                    suggestions.append("2. Patagonia - Premium adventure tours ($250-500/day)\n");
                    suggestions.append("3. Alaska - Luxury wilderness experiences ($300-600/day)\n");
                } else {
                    suggestions.append("1. Costa Rica - Zip-lining and wildlife tours ($60-120/day)\n");
                    suggestions.append("2. Peru - Machu Picchu trek with comfort ($80-150/day)\n");
                    suggestions.append("3. Iceland - Northern lights and glacier tours ($100-200/day)\n");
                }
            }
            case "relaxation", "beach" -> {
                if (budget.equals("budget")) {
                    suggestions.append("1. Thailand - Beautiful beaches and spas ($25-45/day)\n");
                    suggestions.append("2. Bali - Yoga retreats and beach resorts ($30-60/day)\n");
                    suggestions.append("3. Portugal - Coastal towns and affordable luxury ($40-80/day)\n");
                } else if (budget.equals("luxury")) {
                    suggestions.append("1. Maldives - Overwater villas and world-class spas ($500-1500/day)\n");
                    suggestions.append("2. Seychelles - Private island resorts ($400-1200/day)\n");
                    suggestions.append("3. French Polynesia - Luxury bungalows ($600-2000/day)\n");
                } else {
                    suggestions.append("1. Greece - Island hopping and boutique hotels ($80-150/day)\n");
                    suggestions.append("2. Croatia - Adriatic coast and charming towns ($60-120/day)\n");
                    suggestions.append("3. Mexico - Riviera Maya resorts ($70-140/day)\n");
                }
            }
            case "culture" -> {
                if (budget.equals("budget")) {
                    suggestions.append("1. India - Rich heritage and diverse culture ($15-30/day)\n");
                    suggestions.append("2. Morocco - Imperial cities and souks ($25-50/day)\n");
                    suggestions.append("3. Eastern Europe - Historic cities and culture ($30-60/day)\n");
                } else if (budget.equals("luxury")) {
                    suggestions.append("1. Japan - Traditional ryokans and cultural experiences ($200-500/day)\n");
                    suggestions.append("2. Italy - Private tours and luxury accommodations ($250-600/day)\n");
                    suggestions.append("3. Egypt - Luxury Nile cruises and private guides ($150-400/day)\n");
                } else {
                    suggestions.append("1. Turkey - Historic sites and cultural tours ($50-100/day)\n");
                    suggestions.append("2. Spain - Art, architecture, and gastronomy ($70-140/day)\n");
                    suggestions.append("3. China - Ancient wonders and modern cities ($60-120/day)\n");
                }
            }
            default -> {
                suggestions.append("1. France - Diverse experiences from Paris to Provence\n");
                suggestions.append("2. Australia - Cities, nature, and unique wildlife\n");
                suggestions.append("3. Canada - Natural beauty and vibrant cities\n");
            }
        }
        
        // Add duration-specific recommendations
        if (duration <= 3) {
            suggestions.append("\nFor short trips: Focus on one city or region to maximize your time.\n");
        } else if (duration <= 7) {
            suggestions.append("\nFor week-long trips: Combine 2-3 destinations or explore one country thoroughly.\n");
        } else {
            suggestions.append("\nFor extended trips: Consider multi-country itineraries or deep cultural immersion.\n");
        }
        
        return suggestions.toString();
    }
} 