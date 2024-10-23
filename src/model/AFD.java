package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AFD {

    private Set<State> states;
    private Set<Character> alphabet;
    private State initialState;
    private Set<State> finalStates;
    private Map<State, Map<Character, State>> transitionFunction;

    public AFD(Set<State> states, Set<Character> alphabet, State initialState, Set<State> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.initialState = initialState;
        this.finalStates = finalStates;
        this.transitionFunction = new HashMap<>();

        for (State state : this.states){
            this.transitionFunction.put(state, new HashMap<>());
        }
    }

    public void setTransition(State initialState, Character symbol, State nextState) {
        this.transitionFunction.get(initialState).put(symbol, nextState);
    }

    public void verifyAFD(String language){
        State currentState = initialState;

        for (char symbol : language.toCharArray()){
            if(!this.alphabet.contains(symbol)){
                throw new RuntimeException("Symbol Not Found");
            }

            currentState = transitionFunction.get(currentState).get(symbol);
        }

        if (this.finalStates.contains(currentState)){
            System.out.println("Accept");
        }else {
            System.out.println("Reject");
        }
    }
}
