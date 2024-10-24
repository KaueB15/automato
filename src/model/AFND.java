package model;

import java.util.*;

public class AFND {
    private Set<State> states;
    private Set<Character> alphabet;
    private State initalState;
    private Set<State> finalStates;
    private Map<State, Map<Character, Set<State>>> transitionFunction;

    public AFND(Set<State> states, Set<Character> alphabet, State initalState, Set<State> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.initalState = initalState;
        this.finalStates = finalStates;
        this.transitionFunction = new HashMap<>();
        for (State estado : this.states) {
            this.transitionFunction.put(estado, new HashMap<>());
            for (char simbolo : alphabet) {
                this.transitionFunction.get(estado).put(simbolo, new HashSet<>());
            }
            this.transitionFunction.get(estado).put(null, new HashSet<>());
        }
    }

    public void setTransitions(State originState, Character symbol, Set<State> finalStates) {
        this.transitionFunction.get(originState).get(symbol).addAll(finalStates);
    }

    public Set<State> move(State state, Character symbol) {
        return this.transitionFunction.get(state).get(symbol);
    }

    public AFD convertAFNDforAFD() {
        Set<Set<State>> newStates = new HashSet<>();
        Map<Set<State>, Map<Character, Set<State>>> newTransitionFunction = new HashMap<>();
        Set<Set<State>> finalStatesAFD = new HashSet<>();

        Set<State> initalStateAFD = epsilon(Collections.singleton(this.initalState));
        Queue<Set<State>> queue = new LinkedList<>();
        queue.add(initalStateAFD);
        newStates.add(initalStateAFD);

        while (!queue.isEmpty()) {
            Set<State> packageNow = queue.poll();
            newTransitionFunction.put(packageNow, new HashMap<>());

            for (char symbol : this.alphabet) {
                Set<State> result = new HashSet<>();
                for (State state : packageNow) {
                    result.addAll(move(state, symbol));
                }

                Set<State> closureMove = epsilon(result);

                if (!newStates.contains(closureMove)) {
                    newStates.add(closureMove);
                    queue.add(closureMove);
                }

                newTransitionFunction.get(packageNow).put(symbol, closureMove);

                if (!Collections.disjoint(closureMove, this.finalStates)) {
                    finalStatesAFD.add(closureMove);
                }
            }
        }

        Set<State> newStatesAFD = new HashSet<>();
        Map<Set<State>, State> statesMap = new HashMap<>();
        for (Set<State> packageStates : newStates) {
            State newState = new State(packageStates.toString());
            newStatesAFD.add(newState);
            statesMap.put(packageStates, newState);
        }

        Set<State> newFinalStatesAFD = new HashSet<>();
        for (Set<State> packageFinalStates : finalStatesAFD) {
            newFinalStatesAFD.add(statesMap.get(packageFinalStates));
        }

        AFD afd = new AFD(
                newStatesAFD, this.alphabet, statesMap.get(initalStateAFD), newFinalStatesAFD, false
        );

        for (Map.Entry<Set<State>, Map<Character, Set<State>>> transition : newTransitionFunction.entrySet()) {
            State originState = statesMap.get(transition.getKey());
            for (Map.Entry<Character, Set<State>> entryTransition : transition.getValue().entrySet()) {
                afd.setTransition(originState, entryTransition.getKey(), statesMap.get(entryTransition.getValue()));
            }
        }

        return afd;
    }

    private Set<State> epsilon(Set<State> states) {
        Set<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            Set<State> epsilonTransitions = move(state, null);
            for (State stateOne : epsilonTransitions) {
                if (!closure.contains(stateOne)) {
                    closure.add(stateOne);
                    stack.push(stateOne);
                }
            }
        }

        return closure;
    }

}
