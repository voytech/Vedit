package pl.voytech.vedit.core.languages.rt;

import android.app.Notification;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.actions.ActionInvokation;
import pl.voytech.vedit.core.actions.ActionInvokations;
import pl.voytech.vedit.core.actions.CursorMoveAction;
import pl.voytech.vedit.core.actions.EnterAction;
import pl.voytech.vedit.core.actions.SpaceAction;
import pl.voytech.vedit.core.actions.TokenAction;
import pl.voytech.vedit.core.features.FeatureFactory;
import pl.voytech.vedit.core.languages.definition.LangDef;
import pl.voytech.vedit.core.languages.definition.LangPartDef;
import pl.voytech.vedit.core.languages.definition.LangProductionDef;
import pl.voytech.vedit.core.languages.definition.LangTokenDef;
import pl.voytech.vedit.core.languages.definition.LanguageFeatures;
import pl.voytech.vedit.core.languages.definition.LanguageFeaturesProvider;
import pl.voytech.vedit.core.languages.definition.features.MacroFeature;
import pl.voytech.vedit.core.languages.definition.features.SyntaxHighlightFeature;

/**
 * Created by USER on 2016-11-03.
 */

public class JavaLanguage {
    private LanguageFeatures javaLangFeatures = new LanguageFeatures();
    private void defineFeatures(){
        FeatureFactory.i().define("keyword-highlight",SyntaxHighlightFeature.class,Color.BLUE);
        FeatureFactory.i().define("separator-highlight",SyntaxHighlightFeature.class,Color.RED);
        FeatureFactory.i().define("identifier-highlight",SyntaxHighlightFeature.class,Color.GRAY);
        FeatureFactory.i().define("curly-brace-macro",
                                  MacroFeature.class,
                                  new ActionInvokations().add(new ActionInvokation(EnterAction.class))
                                                         .add(new ActionInvokation(EnterAction.class))
                                                         .add(new ActionInvokation(SpaceAction.class))
                                                         .add(new ActionInvokation(TokenAction.class,"}"))
                                                         .add(new ActionInvokation(CursorMoveAction.class, Cursor.Movements.PREV_ROW))
                                  );
    }

    public LangDef definition(){
        defineFeatures();
        LangDef def = new LangDef("java");

        LanguageFeaturesProvider.i().defineLangFeatures("java",javaLangFeatures);

        ////////////////////////////////////////////////////////////////////
        // Keywords
        ////////////////////////////////////////////////////////////////////
        def.addLangPart(createKeyword(def,"abstract",new String[]{"abstract-class","abstract-method"}));
        def.addLangPart(createKeyword(def,"assert",new String[]{""}));
        def.addLangPart(createKeyword(def,"boolean",new String[]{"primitive-arr","primitive-arr-init","primitive","primitive-init"}));
        def.addLangPart(createKeyword(def,"break",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"byte",new String[]{"byte-arr","byte-arr-init","byte-var","byte-var-init"}));
        def.addLangPart(createKeyword(def,"case",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"catch",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"char",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"class",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"const",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"continue",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"default",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"do",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"double",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"else",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"enum",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"extends",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"final",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"finally",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"float",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"for",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"if",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"goto",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"implements",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"import",new String[]{"import-expr"}));
        def.addLangPart(createKeyword(def,"instanceof",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"int",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"interface",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"long",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"native",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"new",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"package",new String[]{"package-expr"}));
        def.addLangPart(createKeyword(def,"private",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"protected",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"public",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"return",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"short",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"static",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"strictfp",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"super",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"switch",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"synchronized",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"this",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"throw",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"throws",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"transient",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"try",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"void",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"volatile",new String[]{"",""}));
        def.addLangPart(createKeyword(def,"while",new String[]{"",""}));
        ////////////////////////////////////////////////////////////////////
        // Separators
        ////////////////////////////////////////////////////////////////////
        def.addLangPart(createSeparator(def,"[","\\[",new String[]{}));
        def.addLangPart(createSeparator(def,"]","\\]",new String[]{}));
        def.addLangPart(createSeparator(def,"(","\\(",new String[]{}));
        def.addLangPart(createSeparator(def,")","\\)",new String[]{}));
        def.addLangPart(withFeature(createSeparator(def,"{","\\{",new String[]{}),"curly-brace-macro"));
        def.addLangPart(createSeparator(def,"}","\\}",new String[]{}));
        def.addLangPart(createSeparator(def,";","\\;",new String[]{}));
        def.addLangPart(createSeparator(def,":","\\:",new String[]{}));
        def.addLangPart(createSeparator(def,",","\\,",new String[]{}));
        def.addLangPart(createSeparator(def,".","\\.",new String[]{}));
        def.addLangPart(createIdentifier());
        ////////////////////////////////////////////////////////////////////
        // Language Productions. Forms valid context-free grammar.
        ////////////////////////////////////////////////////////////////////
        List<String[]> prods = new ArrayList();
        prods.add(new String[]{"boolean","[","]","identifier",";"});
        prods.add(new String[]{"double","[","]","identifier",";"});
        prods.add(new String[]{"float","[","]","identifier",";"});
        prods.add(new String[]{"long","[","]","identifier",";"});
        prods.add(new String[]{"int","[","]","identifier",";"});
        prods.add(new String[]{"byte","[","]","identifier",";"});
        prods.add(new String[]{"short","[","]","identifier",";"});
        createProduction(def,"primitive-arr",prods);
        List<String[]> prods2 = new ArrayList();
        prods2.add(new String[]{"boolean","identifier",";"});
        prods2.add(new String[]{"double","identifier",";"});
        prods2.add(new String[]{"float","identifier",";"});
        prods2.add(new String[]{"long","identifier",";"});
        prods2.add(new String[]{"int","identifier",";"});
        prods2.add(new String[]{"byte","identifier",";"});
        prods2.add(new String[]{"short","identifier",";"});
        createProduction(def,"primitive",prods2);
        List<String[]> prods3 = new ArrayList();
        prods3.add(new String[]{"private","primitive"});
        prods3.add(new String[]{"public","primitive"});
        prods3.add(new String[]{"protected","primitive"});
        prods3.add(new String[]{"private","final","primitive"});
        prods3.add(new String[]{"public","final","primitive"});
        prods3.add(new String[]{"protected","final","primitive"});
        prods3.add(new String[]{"final","primitive"});
        prods3.add(new String[]{"private","static","final","primitive"});
        prods3.add(new String[]{"public","static","final","primitive"});
        prods3.add(new String[]{"protected","static","final","primitive"});
        prods3.add(new String[]{"static","final","primitive"});
        prods3.add(new String[]{"static","primitive"});
        createProduction(def,"primitive-with-modifier",prods3);
        List<String[]> prods4 = new ArrayList();
        prods4.add(new String[]{"private","primitive-arr"});
        prods4.add(new String[]{"public","primitive-arr"});
        prods4.add(new String[]{"protected","primitive-arr"});
        prods4.add(new String[]{"private","final","primitive-arr"});
        prods4.add(new String[]{"public","final","primitive-arr"});
        prods4.add(new String[]{"protected","final","primitive-arr"});
        prods4.add(new String[]{"final","primitive-arr"});
        prods4.add(new String[]{"private","static","final","primitive-arr"});
        prods4.add(new String[]{"public","static","final","primitive-arr"});
        prods4.add(new String[]{"protected","static","final","primitive-arr"});
        prods4.add(new String[]{"static","final","primitive-arr"});
        prods4.add(new String[]{"static","primitive-arr"});
        createProduction(def,"primitive-arr-with-modifier",prods4);
        return def;
    }
    private LangTokenDef createKeyword(LangDef lang,String keyword,String[] startsProductions){
        LangTokenDef keywordToken = new LangTokenDef();
        keywordToken.setPattern("^"+keyword+"$");
        keywordToken.setId(keyword);
        keywordToken.setGroup(LangTokenDef.TokenGroup.KEYWORD);
        javaLangFeatures.add(keywordToken,"keyword-highlight");
        return keywordToken;
    }
    private LangTokenDef createSeparator(LangDef lang,String separator,String pattern,String[] startsProductions){
        LangTokenDef separatorToken = new LangTokenDef();
        separatorToken.setPattern("^"+pattern+"$");
        separatorToken.setId(separator);
        separatorToken.setGroup(LangTokenDef.TokenGroup.SEPARATOR);
        javaLangFeatures.add(separatorToken,"separator-highlight");
        return separatorToken;
    }
    private LangTokenDef createIdentifier(){
        LangTokenDef ident = new LangTokenDef();
        ident.setPattern("^([A-Za-z_]+[A-Za-z0-9_]*)$");
        ident.setId("identifier");
        ident.setGroup(LangTokenDef.TokenGroup.IDENTIFIER);

        javaLangFeatures.add(ident,"identifier-highlight");
        return ident;
    }
    private LangTokenDef[] getFirstToken(LangPartDef langPart){
        LangTokenDef[] tokenDefs = null;
        if (LangTokenDef.class.isAssignableFrom(langPart.getClass())){
            return new LangTokenDef[]{(LangTokenDef)langPart};
        }
        else if (LangProductionDef.class.isAssignableFrom(langPart.getClass())) {
            LangProductionDef productionDef = (LangProductionDef)langPart;
            tokenDefs = productionDef.startingTokens();
        }
        return tokenDefs;
    }

    private LangPartDef createProduction(LangDef lang,String name,List<String[]> productions){
        LangProductionDef productionDef = new LangProductionDef();
        productionDef.setId(name);
        for (String[] production : productions){
            List<LangPartDef> defs = new ArrayList<>();
            for (String kw : production){
                defs.add(lang.getById(kw));
            }
            LangTokenDef[] startingTokens = getFirstToken(defs.get(0));
            if (startingTokens!=null){
                for (LangTokenDef startingToken : startingTokens) {
                    startingToken.getStartsProductions().add(productionDef);
                }
            }
            productionDef.add(defs.toArray(new LangPartDef[]{}));
        }
        lang.addLangPart(productionDef);
        return productionDef;
    }
    private LangTokenDef withFeature(LangTokenDef langTokenDef,String feature){
        javaLangFeatures.add(langTokenDef,feature);
        return langTokenDef;
    }

}
