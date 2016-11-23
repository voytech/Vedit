package pl.voytech.vedit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import pl.voytech.vedit.core.CodeEditor;

public class CodeEditorActivity extends AppCompatActivity {
    private CodeEditor codeEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_editor);
        LinearLayout content = (LinearLayout) findViewById(R.id.editorAct);
        codeEditor =  new CodeEditor(this);
        content.addView(codeEditor);

        codeEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null){
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int k,KeyEvent e){
        if (codeEditor!=null){
            codeEditor.readKey(e);
        }
        return true;
    }
}
