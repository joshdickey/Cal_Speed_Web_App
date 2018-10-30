package speed.model;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private int number;
    private String value;
    private boolean placedOnMatch;
    private boolean hasMatch;
    private transient List<Card> matches;

    public Card() {
        number = -1;
        value = "-1";
        placedOnMatch = false;
        hasMatch = false;
        matches = new ArrayList<>();
    }

    public Card(int number) {
        this.number = number;
        value = convertInttoString(number);
        matches = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getValue() {
        return number;
    }

    private String convertInttoString(int cardNumber){
        String converted = "";
        switch(cardNumber){
            case 1 : converted = "A";
                break;
            case 11 : converted = "J";
                break;
            case 12: converted = "Q";
                break;
            case 13 : converted = "K";
                break;
            default: converted = String.valueOf(cardNumber);
                break;
        }
        return converted;
    }

    public boolean isPlacedOnMatch() {
        return placedOnMatch;
    }

    public void setPlacedOnMatch(boolean placedOnMatch) {
        this.placedOnMatch = placedOnMatch;
    }

    public List<Card> getMatches() {
        return matches;
    }

    public void addMatch(Card matches) {
        this.matches.add(matches);
    }

    public void setMatches(List<Card> matches) {
        this.matches = matches;
    }

    public boolean isHasMatch() {
        return hasMatch;
    }

    public void setHasMatch(boolean hasMatch) {
        this.hasMatch = hasMatch;
    }

    public int compaireTo(Card otherCard){
        return this.number - otherCard.number;
    }

    @Override
    public String toString() {


        return "Card{" +
                "number=" + number +
                ", value='" + value + '\'' +
                ", placedOnMatch=" + placedOnMatch +
                ", hasMatch=" + hasMatch +
//                ", matches=" + matches +
                '}';
    }
}
