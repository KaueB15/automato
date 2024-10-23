package questions;

import model.AFD;
import model.State;

import java.util.HashSet;
import java.util.Set;

public class Question7 {
    public static void main(String[] args) {

        State q0 = new State("q0");
        State q1 = new State("q1");
        State q2 = new State("q2");
        State q3 = new State("q3");
        State q4 = new State("q3");

        Set<State> states = new HashSet<>(Set.of(q0, q1, q2, q3, q4));
        Set<State> finalState = new HashSet<>(Set.of(q1, q2));
        Set<Character> alphabet = new HashSet<>(Set.of('0', '1'));

        AFD afd = new AFD(
                states,
                alphabet,
                q0,
                finalState,
                false
        );

        afd.setTransition(q0, '0', q2);
        afd.setTransition(q0, '1', q1);

        afd.setTransition(q1, '0', q3);
        afd.setTransition(q1, '1', q1);

        afd.setTransition(q2, '0', q2);
        afd.setTransition(q2, '1', q4);

        afd.setTransition(q3, '0', q3);
        afd.setTransition(q3, '1', q1);

        afd.setTransition(q4, '0', q2);
        afd.setTransition(q4, '1', q4);

        afd.verifyAFD("0011");
        afd.verifyAFD("10001000");
        afd.verifyAFD("111011");
        afd.verifyAFD("010");
    }
}
