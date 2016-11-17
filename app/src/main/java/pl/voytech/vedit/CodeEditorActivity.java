package pl.voytech.vedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
    }
    @Override
    public boolean onKeyDown(int k,KeyEvent e){
        if (codeEditor!=null){
            codeEditor.readKey(e);
        }
        return true;
    }
}
