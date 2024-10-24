package questions;

import model.AFD;
import model.AFND;
import model.State;

import java.util.HashSet;
import java.util.Set;

public class Question10 {
    public static void main(String[] args) {

        State q0 = new State("q0");
        State q1 = new State("q1");

        Set<State> states = new HashSet<>(Set.of(q0, q1));
        Set<State> finalState = new HashSet<>(Set.of(q0));
        Set<Character> alphabet = new HashSet<>(Set.of('0', '1'));

        AFND afnd = new AFND(
                states,
                alphabet,
                q0,
                finalState
        );

        afnd.setTransitions(q0, '0', new HashSet<>(Set.of(q0, q1)));
        afnd.setTransitions(q0, '1', new HashSet<>(Set.of(q1)));
        afnd.setTransitions(q1, '1', new HashSet<>(Set.of(q1)));
        afnd.setTransitions(q1, '0', new HashSet<>(Set.of(q1)));

        AFD afd = afnd.convertAFNDforAFD();

        afd.verifyAFD("0011");
        afd.verifyAFD("000000");
        afd.verifyAFD("1111");
        afd.verifyAFD("");
    }
}
