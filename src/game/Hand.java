/**
 * 
 */
package game;

import game.deck.Card;
import game.deck.Suit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import util.comparators.HandOrderCardComparator;

/**
 * @author Patrick Blesi
 *
 */
public class Hand<T extends Card> implements List<T>, Iterable<T> {

	public List<T> cards;
	
	public Hand() {
		cards = new LinkedList<T>();
	}
	
	// Used in deal phase
	@Override
	public boolean add(T c) {
		boolean retval = cards.add(c);
		orderHand();
		return retval;
	}
	
	// Used to add bid cards
	public void addHand(Hand<T> hand) {
		int handSize = hand.size();
		for (int i = 0; i < handSize; i++) {
			this.add(hand.get(i));
		}
	}
	
	public void removeHandFromHand(Hand<T> hand) {
		int handSize = hand.size();
		for (int i = 0; i < handSize; i++) {
			this.remove(hand.get(i));
		}
	}
	
	@Override
	public boolean remove(Object c) {
		return cards.remove(c);
	}
	
	public T getCard(T c) {
		return cards.get(cards.indexOf(c));
	}
	
	public T removeLastCard() {
		return cards.remove(cards.size()-1);
	}
	
	@Override
	public T remove(int index) {
		return cards.remove(index);
	}
	
	public T removeCard(T c) {
		return cards.remove(cards.indexOf(c));
	}
	
	@Override
	public boolean contains(Object o) {
		return cards.contains(o);
	}
	
	@Override
	public void clear() {
		cards.clear();
	}
	
	public void orderHand() {
		Collections.sort(cards, new HandOrderCardComparator());
	}
	
	@Override
	public int size() {
		return cards.size();
	}
	
	public List<T> getNonPointCards() {
		List<T> nonPointCards = new ArrayList<T>();
		for (T c : cards) {
			if (c.numberOfPoints == 0) {
				nonPointCards.add(c);
			}
		}
		return nonPointCards;
	}
	
	public List<T> getPlayableCards(Suit leadSuit, Suit trump) {
		List<T> playableCards = new ArrayList<T>();
		
		for (T c : cards) {
			
			if (c.isTrumpOrS(trump) && leadSuit.equals(trump)) {
				playableCards.add(c);
			} else if (c.suit.equals(leadSuit)) {
				playableCards.add(c);
			}
			
		}
		
		return playableCards.size() != 0 ? playableCards : getCardList();
	}
	
	public List<T> getAllCardsMatchingSuit(Suit s) {
		
		List<T> cardList = new LinkedList<T>();
		
		for (T c : this.cards) {
			if (c.suit.equals(s)) {
				cardList.add(c);
			}
		}
		
		return cardList;
		
	}
	
	public List<T> getAllTrumpCards(Suit trump) {
		
		List<T> cardList = new LinkedList<T>();
		
		// Always return s
		List<T> ssCards = this.getAllCardsMatchingSuit(new Suit("s"));
		cardList.addAll(ssCards);
		
		// Add other trump cards, but don't add s/s twice
		if (!trump.equals(new Suit("s"))) {
			cardList.addAll(this.getAllCardsMatchingSuit(trump));
		}
		
		return cardList;
		
	}
	
	public Set<Suit> getAllUniqueSuits() {
		
		Set<Suit> suitSet = new HashSet<Suit>();
		
		for (Card c : cards) {
			suitSet.add(c.suit);
		}
		
		return suitSet;
		
	}
	
	private List<T> getCardList() {
		List<T> cardList = new ArrayList<T>();
		for (T c : this.cards) {
			cardList.add(c);
		}
		return cardList;
	}
	
	@Override
	public Iterator<T> iterator() {
		return cards.iterator();
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
	
 	public static <U extends Card> Hand<U> deepCopyHand(Hand<U> hand) {
		
		Hand<U> deepCopyHand = new Hand<U>();
		
		for (U card : hand) {
			deepCopyHand.add(U.deepCopyCard(card));
		}
	
		return deepCopyHand;
		
	}

 	// List Methods
 	
	/* (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, T element) {
		this.cards.add(index, element);
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends T> c) {
		return this.cards.addAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return this.cards.addAll(index, c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return this.cards.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	@Override
	public T get(int index) {
		return this.cards.get(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {
		return this.cards.indexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {
		return this.cards.lastIndexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<T> listIterator() {
		return this.cards.listIterator();
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<T> listIterator(int index) {
		return this.cards.listIterator(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return this.cards.removeAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return this.cards.retainAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public T set(int index, T element) {
		return this.cards.set(index, element);
	}

	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return this.cards.subList(fromIndex, toIndex);
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {
		return this.cards.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@Override
	public <U> U[] toArray(U[] a) {
		return this.cards.toArray(a);
	}
	
}
