package pl.voytech.vedit.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import pl.voytech.vedit.R;
import pl.voytech.vedit.core.actions.BackspaceAction;
import pl.voytech.vedit.core.actions.BuildTokenAction;
import pl.voytech.vedit.core.actions.CursorMoveAction;
import pl.voytech.vedit.core.actions.EnterAction;
import pl.voytech.vedit.core.actions.LangAnalyzerAction;
import pl.voytech.vedit.core.actions.LangSeparatorAction;
import pl.voytech.vedit.core.actions.SpaceAction;
import pl.voytech.vedit.core.filters.DefaultKeyEventActions;
import pl.voytech.vedit.core.filters.LanguageKeyEventActions;
import pl.voytech.vedit.core.filters.LanguageParserFilter;
import pl.voytech.vedit.core.filters.SpecialKeyEventActions;
import pl.voytech.vedit.core.languages.definition.LangDef;
import pl.voytech.vedit.core.languages.definition.features.SyntaxHighlightFeature;
import pl.voytech.vedit.core.languages.definition.features.renderers.SyntaxHighlightFeatureRenderer;
import pl.voytech.vedit.core.languages.rt.JavaLanguage;
import pl.voytech.vedit.core.renderers.core.RenderersRegistry;
import pl.voytech.vedit.core.renderers.impl.CursorRenderer;
import pl.voytech.vedit.core.renderers.impl.EditorBufferRenderer;
import pl.voytech.vedit.core.renderers.impl.TokenRenderer;

/**
 * TODO: document your custom view class.
 */
public class CodeEditor extends View {
    private final EditorConfig config = new EditorConfig();
    private final EditorState state = new EditorState(config);
    private final EditorBuffer buffer = new EditorBuffer(state);
    private final EditorActions actions = new EditorActions();
    private final KeyEventActions keyEventMapping = new SpecialKeyEventActions();
    private final TokenReader tokenBuilder = new TokenReader(buffer,actions,state,keyEventMapping);

    public CodeEditor(Context context) {
        super(context);
        init(null, 0);
    }

    public CodeEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CodeEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void readKey(KeyEvent event){
        tokenBuilder.read(event);
        System.out.println(buffer);
        this.invalidate();
    }


    private void toggleKeyboard(boolean toggle){
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        LangDef langDef = new JavaLanguage().definition();
        tokenBuilder.addFilter(new LanguageParserFilter(langDef));
        tokenBuilder.addKeyEventActions(new SpecialKeyEventActions());
        tokenBuilder.addKeyEventActions(new LanguageKeyEventActions(langDef));
        tokenBuilder.addKeyEventActions(new DefaultKeyEventActions());
        tokenBuilder.chain(new TokenProductionReader());
        config.setBackgroundColor(Color.BLACK);
        config.setBaseColor(Color.BLACK);
        config.setTopMargin(20);
        actions.addMapping(new BackspaceAction());
        actions.addMapping(new BuildTokenAction(langDef));
        actions.addMapping(new EnterAction());
        actions.addMapping(new CursorMoveAction());
        actions.addMapping(new SpaceAction());
        actions.addMapping(new LangSeparatorAction(langDef));
        LangAnalyzerAction analyzerAction = new LangAnalyzerAction();
        analyzerAction.setLanguage(langDef);
        actions.addMapping(analyzerAction);
        // Need to explicitly bind renderable element with its renderer.
        // This kind of decoupling allows to complete divide logic from rendering.
        RenderersRegistry.i().register(Cursor.class,new CursorRenderer());
        RenderersRegistry.i().register(Token.class,new TokenRenderer());
        RenderersRegistry.i().register(EditorBuffer.class,new EditorBufferRenderer());
        RenderersRegistry.i().register(SyntaxHighlightFeature.class,new SyntaxHighlightFeatureRenderer());

        toggleKeyboard(true);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("View has been clicked");
            }
        });
        this.requestFocus();

        //final TypedArray a = getContext().obtainStyledAttributes(
        //        attrs, R.styleable.CodeEditor, defStyle, 0);
       //a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RenderersRegistry.i().render(buffer,canvas,config);
        RenderersRegistry.i().render(state.getCursor(),canvas,config);

    }
}
