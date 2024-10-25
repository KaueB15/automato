package questions;

import model.AFD;
import model.State;

import java.util.HashSet;
import java.util.Set;

public class Question3 {
    public static void main(String[] args) {

        State q0 = new State("q0");
        State q1 = new State("q1");

        Set<State> states = new HashSet<>(Set.of(q0, q1));
        Set<State> finalState = new HashSet<>(Set.of(q0));
        Set<Character> alphabet = new HashSet<>(Set.of('0', '1'));

        AFD afd = new AFD(
                states,
                alphabet,
                q0,
                finalState,
                true
        );

        afd.setTransition(q0, '0', q1);
        afd.setTransition(q0, '1', q1);
        afd.setTransition(q1, '0', q1);
        afd.setTransition(q1, '1', q1);

        afd.verifyAFD("0011");
        afd.verifyAFD("000000");
        afd.verifyAFD("1111");
        afd.verifyAFD("");
    }
}
