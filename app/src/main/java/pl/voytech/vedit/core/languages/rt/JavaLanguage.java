package pl.voytech.vedit.core.languages.rt;

import android.app.Notification;
import android.graphics.Color;

import java.util.Arrays;

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
        def.addLangPart(createKeyword("abstract",new String[]{"abstract-class","abstract-method"}));
        def.addLangPart(createKeyword("assert",new String[]{""}));
        def.addLangPart(createKeyword("boolean",new String[]{"boolean-arr","boolean-arr-init","boolean-var","boolean-var-init"}));
       // createProduction(def,"boolean-arr","boolean","[","]");
        def.addLangPart(createKeyword("break",new String[]{"",""}));
        def.addLangPart(createKeyword("byte",new String[]{"byte-arr","byte-arr-init","byte-var","byte-var-init"}));
        def.addLangPart(createKeyword("case",new String[]{"",""}));
        def.addLangPart(createKeyword("catch",new String[]{"",""}));
        def.addLangPart(createKeyword("char",new String[]{"",""}));
        def.addLangPart(createKeyword("class",new String[]{"",""}));
        def.addLangPart(createKeyword("const",new String[]{"",""}));
        def.addLangPart(createKeyword("continue",new String[]{"",""}));
        def.addLangPart(createKeyword("default",new String[]{"",""}));
        def.addLangPart(createKeyword("do",new String[]{"",""}));
        def.addLangPart(createKeyword("double",new String[]{"",""}));
        def.addLangPart(createKeyword("else",new String[]{"",""}));
        def.addLangPart(createKeyword("enum",new String[]{"",""}));
        def.addLangPart(createKeyword("extends",new String[]{"",""}));
        def.addLangPart(createKeyword("final",new String[]{"",""}));
        def.addLangPart(createKeyword("finally",new String[]{"",""}));
        def.addLangPart(createKeyword("float",new String[]{"",""}));
        def.addLangPart(createKeyword("for",new String[]{"",""}));
        def.addLangPart(createKeyword("if",new String[]{"",""}));
        def.addLangPart(createKeyword("goto",new String[]{"",""}));
        def.addLangPart(createKeyword("implements",new String[]{"",""}));
        def.addLangPart(createKeyword("import",new String[]{"import-expr"}));
        def.addLangPart(createKeyword("instanceof",new String[]{"",""}));
        def.addLangPart(createKeyword("int",new String[]{"",""}));
        def.addLangPart(createKeyword("interface",new String[]{"",""}));
        def.addLangPart(createKeyword("long",new String[]{"",""}));
        def.addLangPart(createKeyword("native",new String[]{"",""}));
        def.addLangPart(createKeyword("new",new String[]{"",""}));
        def.addLangPart(createKeyword("package",new String[]{"package-expr"}));
        def.addLangPart(createKeyword("private",new String[]{"",""}));
        def.addLangPart(createKeyword("protected",new String[]{"",""}));
        def.addLangPart(createKeyword("public",new String[]{"",""}));
        def.addLangPart(createKeyword("return",new String[]{"",""}));
        def.addLangPart(createKeyword("short",new String[]{"",""}));
        def.addLangPart(createKeyword("static",new String[]{"",""}));
        def.addLangPart(createKeyword("strictfp",new String[]{"",""}));
        def.addLangPart(createKeyword("super",new String[]{"",""}));
        def.addLangPart(createKeyword("switch",new String[]{"",""}));
        def.addLangPart(createKeyword("synchronized",new String[]{"",""}));
        def.addLangPart(createKeyword("this",new String[]{"",""}));
        def.addLangPart(createKeyword("throw",new String[]{"",""}));
        def.addLangPart(createKeyword("throws",new String[]{"",""}));
        def.addLangPart(createKeyword("transient",new String[]{"",""}));
        def.addLangPart(createKeyword("try",new String[]{"",""}));
        def.addLangPart(createKeyword("void",new String[]{"",""}));
        def.addLangPart(createKeyword("volatile",new String[]{"",""}));
        def.addLangPart(createKeyword("while",new String[]{"",""}));
        ////////////////////////////////////////////////////////////////////
        // Separators
        ////////////////////////////////////////////////////////////////////
        def.addLangPart(createSeparator("[","\\[",new String[]{}));
        def.addLangPart(createSeparator("]","\\]",new String[]{}));
        def.addLangPart(createSeparator("(","\\(",new String[]{}));
        def.addLangPart(createSeparator(")","\\)",new String[]{}));
        def.addLangPart(withFeature(createSeparator("{","\\{",new String[]{}),"curly-brace-macro"));
        def.addLangPart(createSeparator("}","\\}",new String[]{}));
        def.addLangPart(createSeparator(";","\\;",new String[]{}));
        def.addLangPart(createSeparator(":","\\:",new String[]{}));
        def.addLangPart(createSeparator(",","\\,",new String[]{}));
        def.addLangPart(createSeparator(".","\\.",new String[]{}));
        def.addLangPart(createIdentifier());
        return def;
    }
    private LangTokenDef createKeyword(String keyword,String[] startsProductions){
        LangTokenDef keywordToken = new LangTokenDef();
        keywordToken.setPattern("^"+keyword+"$");
        keywordToken.setId(keyword);
        keywordToken.setGroup(LangTokenDef.TokenGroup.KEYWORD);
        javaLangFeatures.add(keywordToken,"keyword-highlight");
        return keywordToken;
    }
    private LangTokenDef createSeparator(String separator,String pattern,String[] startsProductions){
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
    private LangPartDef createProduction(LangDef lang,String name,String... tokens){
        LangProductionDef productionDef = new LangProductionDef();
        productionDef.setId(name);
        for (String token : tokens){
            productionDef.add(lang.getById(token));
        }
        lang.addLangPart(productionDef);
        return productionDef;
    }
    private LangTokenDef withFeature(LangTokenDef langTokenDef,String feature){
        javaLangFeatures.add(langTokenDef,feature);
        return langTokenDef;
    }

}
