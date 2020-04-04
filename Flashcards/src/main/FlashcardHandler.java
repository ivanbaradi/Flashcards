package main;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * Flashcard which includes the question and it answer
 * @author ninjagodivan
 */
class Flashcard{
	String question, answer;
}

/***
 * Flashcard Handler API 
 * @author ninjagodivan
 *
 */
public class FlashcardHandler {
	
	/*
	 * Current index position of the flashcard list
	 * Constraints: 0 <= flashcardTracker < set size
	 */
	private int flashcardTracker;
	
	//Flashcard List that the user selected
	private ArrayList <Flashcard> flashcard_list;
	
	//0 = Question, 1 = Answer
	private int flashcardIndicator;
	
	//Name of the flashcard set
	private String flashcardSet;

	/**
	 * Initializes flashcardTracker to reference the
	 * first flashcard and flashcard list to be the
	 * set that the user selected
	 * @param flashcardSet
	 */
	public FlashcardHandler(String flashcardSet) {
		flashcardTracker = 0;
		flashcardIndicator = 0;
		this.flashcardSet = flashcardSet;
		flashcard_list = DatabaseHandler.getAllFlashcards(flashcardSet);
	}
	
	/**
	 * Gets the name of the flashcard set
	 * @return
	 */
	public String getFlashcardSetName() {
		return this.flashcardSet;
	}
	
	/**
	 * Gets the array of flashcards
	 * @return
	 */
	public ArrayList<Flashcard> getFlashcardSet(){
		return this.flashcard_list;
	}
	
	/**
	 * Goes to the next flashcard
	 */
	public void nextFlashcard() {
		if(flashcardTracker + 1 != flashcard_list.size())
			flashcardTracker++;
		System.out.println("Current flashcard position at: " + flashcardTracker);
	}
	
	/**
	 * Goes to the previous flashcard
	 */
	public void prevFlashcard() {
		
		if(flashcardTracker - 1 != -1)
			flashcardTracker--;
		
		System.out.println("Current flashcard position at: " + flashcardTracker);
	}
	
	/**
	 * Changes the flashcard
	 */
	public void changeFlashcard(String question) {
	//	flashcardTracker = flashcard_list
	}
	
	/**
	 * Gets the current flashcard
	 * @return
	 */
	public Flashcard getFlashcard() {
		return flashcard_list.get(flashcardTracker);
	}
	
	/**
	 * Gets the question of the current flashcard
	 * @return
	 */
	public String getQuestion() {
		return flashcard_list.get(flashcardTracker).question;
	}
	
	/**
	 * Gets the answer of the current flashcard
	 * @return
	 */
	public String getAnswer() {
		return flashcard_list.get(flashcardTracker).answer;
	}
	
	/**
	 * Switches from question to answer (vice-versa)
	 */
	public void switchIndicator() {
		
		if(flashcardIndicator == 0)
			flashcardIndicator = 1;
		else
			flashcardIndicator = 0;
	}
	
	/**
	 * Returns the flashcard indicator
	 * @return
	 */
	public int getFlashcardIndicator() {
		return flashcardIndicator;
	}
	
	/**
	 * Returns the current index of the
	 * flashcard
	 * @return
	 */
	public int getFlashcardTracker() {
		return flashcardTracker;
	}
	
	/**
	 * Creates a flashcard view whenever
	 * the user switches the flashcard or
	 * changes its question to answer
	 * (vice-versa).
	 * @return
	 */
	public JPanel createFlashcardView() {
		
		//Flashcard Structure
		JPanel flashcard_view = new JPanel(new GridBagLayout());
		flashcard_view.setBackground(Color.WHITE);
		GridBagConstraints c = new GridBagConstraints();
		
		String description;
				
		//Gets either the question or answer of the flashcard
		if(getFlashcardIndicator() == 0)
			 description = getQuestion();
		else
			 description = getAnswer();

		//String size of the question or answer
		int descriptionSize = description.toCharArray().length;
		
		/*
		 * If the string has more than 30 characters, then string must be broken into substrings.
		 * Otherwise, just add the description.
		 * 
		 * NOTE: If you decide to change the amount of characters per line, make SURE that you
		 * change all values that:
		 * - increment "i"; ex) i += 10
		 * - compare with the description size; ex) if(descriptionSize > 10) 
		 * - add to "i"; ex) description.substring(i, i + 10)
		 * - checks if the last index of the substring and the next 
		 *   one after contains a non-space character
		 *   ex) if(description.charAt(i + 9) != ' ' && description.charAt(i + 10) != ' ')
		 * 
		 * Otherwise, you will MISS some trailing characters!!!
		 */
		if(descriptionSize > 30) {
			for(int i = 0, j = 0; i < descriptionSize; i += 30, j++) {
								
				//Label that will be added in the flashcard
				JLabel descriptionText;
				//Substring of the string of question/answer
				String _substring = null;
				//False if the word hasn't move to the next line
				boolean hasMovedtoNextLine = false;
				
				/*
				 * Subtracts the value that references the last index of the substring.
				 * It's responsible for repositioning i before the next iteration to
				 * get the rest of the characters.
				 */
				int decrementor = 0;
				
				if(i + 30 < descriptionSize) {
											
					//Takes 30 consecutive letters from the description
					_substring = description.substring(i, i + 30);
					
					
					/* 
					 * Must check if the last char doesn't contain
					 * any character besides a space AND that there
					 * is not a space after that letter. If so,
					 * then the word that is associated with that
					 * letter needs to be moved to the next line
					 * 
					 */
					if(description.charAt(i + 29) != ' ' && description.charAt(i + 30) != ' ') {
						
						
						//Gets the last index of the substring
						int index = 29;
						
						//Loops through the list backwards to find a space
						do {
							//System.out.println("Index: " + index);
							index--;
							decrementor++;
							if(index == -1)
								break;
							
						} while(_substring.charAt(index) != ' ');
						
						/*
						 * Updates the substring to where it ends with a space.
						 * Otherwise, no letters are to be moved to the next line.
						 */
						if(index > -1) {
							_substring = description.substring(i, (i + 30) - decrementor);
							hasMovedtoNextLine = true;
						}
					}
					
				}
				else
					_substring = description.substring(i, descriptionSize);
								
				descriptionText = new JLabel(_substring);
				descriptionText.setFont(new Font("Helvetica", Font.PLAIN, 14));
				c.gridx = 0;
				c.gridy = j;
				flashcard_view.add(descriptionText, c);
				
				//Moves 'i' to get all letters to the be the part of the next substring
				if(hasMovedtoNextLine) 
					i -= decrementor;
			}
		} else {
			JLabel descriptionText = new JLabel(description);
			descriptionText.setFont(new Font("Helvetica", Font.PLAIN, 14));
			c.gridx = 0;
			c.gridy = 0;
			flashcard_view.add(descriptionText, c);
		}

			
		return flashcard_view;
	}
}