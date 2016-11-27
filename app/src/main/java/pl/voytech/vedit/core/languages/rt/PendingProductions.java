package pl.voytech.vedit.core.languages.rt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.languages.definition.LangProductionDef;
import pl.voytech.vedit.core.languages.definition.LangTokenDef;

/**
 * Created by USER on 2016-11-27.
 */

public class PendingProductions {
    private final List<UUID> consumedTokens = new ArrayList<>();
    private final List<MaybeProduction> analysis = new ArrayList<>();
    private final Map<UUID,List<MaybeProduction>> startedAnalysis = new HashMap<>();

    public MaybeProduction getMaybeProduction(Token token, LangTokenDef langTokenDef, LangProductionDef production) {
        if (startedAnalysis.containsKey(token.getId())) {
            for (MaybeProduction maybeProduction : startedAnalysis.get(token.getId())) {
                if (maybeProduction.def().equals(production)) {
                    return maybeProduction;
                }
            }
        }
        MaybeProduction maybeProduction = new MaybeProduction(production, this);
        if (!startedAnalysis.containsKey(token.getId())){
            startedAnalysis.put(token.getId(),new ArrayList<MaybeProduction>());
        }
        startedAnalysis.get(token.getId()).add(maybeProduction);
        return maybeProduction;
    }

    public void consume(Token token, LangTokenDef langTokenDef){
        for (MaybeProduction mb : analysis){
            if (mb.getStatus()== MaybeProduction.Status.PENDING){
                mb.consume(token,langTokenDef);
            }
        }
    }

}
