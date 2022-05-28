package OthelloApp.model.rules;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

public class MovesAvailableCondition implements Condition {

    @Override
    public boolean evaluate(Facts facts) {
        if (facts.get("possibleMoveList") == null){
            System.out.println("Nothing in here");
        }
        return facts.get("possibleMoveList") != null;
    }

    public static MovesAvailableCondition computerHasAvailableMoves(){
        return new MovesAvailableCondition();
    }
}
