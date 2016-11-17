package pl.voytech.vedit.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pl.voytech.vedit.core.renderers.core.Renderable;

/**
 * Created by USER on 2016-10-26.
 */

public class EditorBuffer implements Renderable,EditorApi {
    interface TokenListener {
        void ready(Token token);
    }
    public interface TokenVisitor{
        void visit(Token token, int index);
    }
    private final Map<Integer,List<Token>> tokens = new HashMap<Integer,List<Token>>();
    private final Map<UUID,Token> tokensById = new HashMap<>();
    private final EditorState state;
    private TokenListener listener;
    private final Comparator<Token> COLUMN_COMPARATOR = new Comparator<Token>() {
        @Override
        public int compare(Token token, Token t1) {
            return token.getStartColumn() - t1.getStartColumn();
        }
    };
    public EditorBuffer(EditorState state) {
        this.state = state;
    }

    public EditorState getState(){
        return state;
    }

    public void setTokenListener(TokenListener listener){
        this.listener = listener;
    }

    private List<Token> assertRow(int row) {
        if (!tokens.containsKey(row)) {
            tokens.put(row, new ArrayList<Token>());
        }
        return tokens.get(row);
    }

    private void add(Token token){
        int s = token.getEndRow();
        int e = token.getStartRow();
        assertRow(s).add(token);
        Collections.sort(tokens.get(s),COLUMN_COMPARATOR);
        if (s!=e){
            assertRow(e).add(token);
            Collections.sort(tokens.get(e),COLUMN_COMPARATOR);
        }
        tokensById.put(token.getId(),token);
    }
    public Token newToken(){
        Token token = new Token(state.getCursor());
        add(token);
        return token;
    }

    public Token newToken(String s) {
        Token token = newToken();
        token.update(s);
        return token;
    }

    public void delete(){
        Token current = this.pointedBy(this.state.getCursor());
        if (current!=null){
            delete(current);
        }
    }

    @Override
    public void insert(char character) {
        Token current = this.currentToken();
        if (current==null || (current!=null && current.getState() == Token.State.FINISHED && current.rightBefore(state.getCursor()))){
            current = newToken();
        }
        current.insert(character,state.getCursor());
    }

    @Override
    public boolean removeLeft() {
        Token current = this.currentToken();
        if (current!=null){
            if (!current.removeLeft(state.getCursor())){
                this.delete(current);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRight() {
        Token current = this.pointedBy(state.getCursor());
        if (current!=null){
            if (!current.removeRight(state.getCursor())){
                this.delete(current);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean merge() {
        Cursor c = state.getCursor();
        Token rightBeforeToken = tokenBefore(c);
        Token pointedToken = pointedBy(c);
        if (rightBeforeToken!=null && pointedToken!=null){
            if (pointedToken.getStartColumn() == c.getColumn()) {
                delete(pointedToken);
                rightBeforeToken.update(rightBeforeToken.getValue() + pointedToken.getValue());
                if (this.listener!=null){
                    this.listener.ready(rightBeforeToken);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean split() {
        Cursor c = state.getCursor();
        Token token = this.pointedBy(c);
        if (token!=null) {
            Token newToken = token.split(c);
            add(newToken);
            if (this.listener!=null){
                this.listener.ready(token);
                this.listener.ready(newToken);
            }
            return true;
        }
        return false;
    }

    @Override
    public void end() {
        Token current = tokenBefore(state.getCursor());
        if (current!=null){
            current.setState(Token.State.FINISHED);
            if (this.listener!=null){
                this.listener.ready(current);
            }
        } else throw new RuntimeException("Can only end tokens right before cursor position!");
    }

    @Override
    public void end(Token token) {
        if (token.rightBefore(state.getCursor())) {
            token.setState(Token.State.FINISHED);
            if (this.listener != null) {
                this.listener.ready(token);
            }
        } else throw new RuntimeException("Can only end tokens right before cursor position!");
    }

    @Override
    public void update(Token token, String value) {
        token.update(value);
    }

    @Override
    public void move(Token token, int colOffs, int rowOffs){
        delete(token);
        token.move(colOffs,rowOffs);
        add(token);
    }

    @Override
    public boolean merge(Token t1, Token t2) {

        return false;
    }

    @Override
    public boolean split(Token token, char on) {
        return false;
    }

    @Override
    public boolean split(Token token, int index) {
        return false;
    }

    public void delete(Token token){
        int sR = token.getStartRow();
        int eR = token.getEndRow();
        Iterator<Token> tokenSIterator = tokens.get(sR).iterator();
        while (tokenSIterator.hasNext()){
            Token t = tokenSIterator.next();
            if (t.equals(token)){
                tokenSIterator.remove();
            }
        }
        if (sR!=eR) {
            Iterator<Token> tokenEIterator = tokens.get(eR).iterator();
            while (tokenEIterator.hasNext()) {
                Token t = tokenEIterator.next();
                if (t.equals(token)) {
                    tokenEIterator.remove();
                }
            }
        }
        UUID rKey = null;
        for (Map.Entry<UUID,Token> entry: tokensById.entrySet()){
            if (token.equals(entry.getValue())){
                rKey = entry.getKey();
                break;
            }
        }
        if (rKey!=null){
            tokensById.remove(rKey);
        }
    }

    public Token pointedBy(Cursor cursor){
        List<Token> rowTokens = assertRow(cursor.getRow());
        for (Token token : rowTokens){
            if (token.pointedBy(cursor)){
                return token;
            }
        }
        return null;
    }

    public Token tokenBefore(Cursor cursor){
        List<Token> rowTokens = assertRow(cursor.getRow());
        for (Token token : rowTokens){
            if (token.rightBefore(cursor)){
                return token;
            }
        }
        return null;
    }

    public Token currentToken(){
        Cursor cursor = this.state.getCursor();
        Token current = this.pointedBy(cursor);
        if (current == null) {
            current = this.tokenBefore(cursor);
        }
        return current;
    }

    public void allTokens(TokenVisitor visitor){
        for (List<Token> row : tokens.values()){
            for (int i=0;i<=row.size()-1;i++){
                visitor.visit(row.get(i),i);
            }
        }
    }
    public void allTokensInRow(TokenVisitor visitor,int row){
        if (tokens.containsKey(row)) {
            for (int i = 0; i <= tokens.get(row).size() - 1; i++) {
                visitor.visit(tokens.get(row).get(i), i);
            }
        }
    }
    public void allTokensAfter(Cursor cursor,TokenVisitor visitor){
        List<Token> reversed = new ArrayList<>();
        int row = cursor.getRow();
        for (int i=row;i<=this.tokens.size();i++){
            List<Token> ts = tokens.get(i);
            for (int j=0;j<ts.size();j++){
                Token tkn = ts.get(j);
                if (tkn.getStartRow() == cursor.getRow()){
                   if (tkn.getStartColumn() >= cursor.getColumn()){
                       reversed.add(tkn);
                   }
                } else if (tkn.getStartRow() > cursor.getRow()){
                    reversed.add(tkn);
                }
            }
        }
        for (int i = reversed.size()-1;i>=0;i--){
            visitor.visit(reversed.get(i),i);
        }
    }
    public void allTokensInRowAfter(Cursor cursor,TokenVisitor visitor,int row,boolean includeCursorPos){
        List<Token> ts = tokens.get(row);
        for (int i=ts.size()-1;i>=0;i--){
            Token token = ts.get(i);
            if (includeCursorPos? token.getStartColumn() >= cursor.getColumn() : token.getStartColumn() > cursor.getColumn()){
                visitor.visit(token,i);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Integer line : this.tokens.keySet()){
            List<Token> ts = this.tokens.get(line);
            builder.append("\nLine ( ").append(line).append(" )").append(" Size").append(" ( ").append(ts.size()).append(" ) ").append("\n");
            for (Token token:ts){
                String value = token.getValue();
                builder.append("|");
                builder.append(" [ ").append(token.getStartRow()).append(":").append(token.getStartColumn()).append(":").append(token.getEndColumn());
                builder.append("] ").append(value.trim().isEmpty() ? "SPACE":value);
                builder.append("|");
            }
            builder.append("\nEnd Line ( ").append(line).append(" )\n");
        }
        return builder.toString();
    }

}