/*
 * PP および FC 実行＆ページ作成クラス
 */

// パッケージ宣言
package cn.com.intra_mart.system.display;

// クラスロード
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import jdk.nashorn.internal.runtime.ScriptObject;
import jp.co.intra_mart.system.display.ScriptScope;
import jp.co.intra_mart.system.javascript.Context;
import jp.co.intra_mart.system.javascript.Function;
import jp.co.intra_mart.system.javascript.JavaScriptException;
import jp.co.intra_mart.system.javascript.ScriptRuntime;
import jp.co.intra_mart.system.javascript.Scriptable;
import jp.co.intra_mart.system.javascript.ScriptableObject;
import jp.co.intra_mart.system.javascript.imapi.JsUtil;
import jp.co.intra_mart.system.javascript.imapi.exception.JavaScriptRedirectError;
import jp.co.intra_mart.system.jssp.provider.application.JSSPScriptBuilder;
import jp.co.intra_mart.system.jssp.provider.application.JSSPViewBuilder;
import jp.co.intra_mart.system.jssp.script.view.Composition;
import jp.co.intra_mart.system.jssp.validation.Validation;
import jp.co.intra_mart.system.jssp.validation.ValidationResultSet;

public class Content implements Serializable {
    // インスタンス変数
    private final String key; // ページ・ソース名

    private ScriptScope scriptScope; // JavaScript ソース

    private Composition composition; // view ソース

    /**
     * コンストラクタ 引数にはソース名を指定。ソース名は、拡張子を省略したもの。 ソースパスからの相対で、ディレクトリ名およびファイル名の区切りを 　'/'　で区切った形式。
     * @param name ソース名
     */
    public Content(final String name) {
        this.key = name; // 名前の登録
    }

    // TODO
    /**
     * 调用指定jssp画面(init方法)，获得指定的该画面的全局Bind变量<br>
     * 考虑用于生成jssp画面功能解耦时，方便封装初期表示用的接口<br>
     * 例如：调用画面初期化，基于获得Bind变量封装初始化数据返回Json。
     * <b>该函数只能用户jssp开发模式的后台js处理</b>
     * 
     * @param value 传递给调用对象画面的init入口函数的参数。一般为request
     * @param bindDataKey 需要获得Bind变量名
     * @return Bind变量对象
     * @throws JavaScriptException
     * @throws JavaScriptRedirectError
     * @throws FileNotFoundException
     */
    public Object getBindData(final Object value, String bindDataKey) throws JavaScriptException, JavaScriptRedirectError, FileNotFoundException {
        // ページの初期化関数を実行
    	final Context cx = Context.getCurrentContext();
    	Object[] args = { value };
        try {
            try {
                final ScriptScope scope = this.getScope();
                if (ScriptableObject.hasProperty(scope, "init")) {
                    final ValidationResultSet res = Validation.getInstance().validate(cx, scope, "init");
                    if (res.isValid()) {
                        scope.call(cx, "init", args);
                    } else {
                        Validation.getInstance().callErrorFunction(cx, scope, "init", args, res);
                    }
                }
                try {
                	Object obj = scope.get(bindDataKey, scope);
                	if (obj == Scriptable.NOT_FOUND) {
                		return null;
                	}
                    return obj;
                } finally {
                    if (ScriptableObject.hasProperty(scope, "close")) {
                        scope.call(cx, "close", args);
                    }
                }
            } catch (final FileNotFoundException fnfe) {
            	throw fnfe;
            }
        } catch (final InstantiationException ie) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ie);
            throw ise;
        } catch (final IllegalAccessException iae) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(iae);
            throw ise;
        } catch (final IOException ioe) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ioe);
            throw ise;
        } catch (final ClassNotFoundException cnfe) {
            final FileNotFoundException fnfe = new FileNotFoundException("Source not found: " + this.getSourceName());
            fnfe.initCause(cnfe);
            throw fnfe;
        }
    }
    
    // TODO
    /**
     * 调用指定jssp画面(init方法)，取得指定jssp画面后台处理中的全局Bind变量<br>
     * 考虑用于生成jssp画面功能解耦时，方便封装初期表示用的接口<br>
     * 例如：调用画面初期化，基于获得Bind变量封装初始化数据返回Json。
     * <b>该函数只能用户jssp开发模式的后台js处理</b>
     * 
     * @param value 传递给调用对象画面的init入口函数的参数。一般为request
     * @return Bind变量对象
     * @throws JavaScriptException
     * @throws JavaScriptRedirectError
     * @throws FileNotFoundException
     */
    public Object getAllBindData(final Object value) throws JavaScriptException, JavaScriptRedirectError, FileNotFoundException {
        // ページの初期化関数を実行
    	final Context cx = Context.getCurrentContext();
    	Object[] args = { value };
        try {
            try {
                final ScriptScope scope = this.getScope();
                if (ScriptableObject.hasProperty(scope, "init")) {
                    final ValidationResultSet res = Validation.getInstance().validate(cx, scope, "init");
                    if (res.isValid()) {
                        scope.call(cx, "init", args);
                    } else {
                        Validation.getInstance().callErrorFunction(cx, scope, "init", args, res);
                    }
                }
                try {
                	ScriptableObject result = new ScriptableObject(scope, null) {
                        /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
                        public String getClassName() {
                            return "Temp";
                        }
                    };
                    
                	Object[] objs = scope.getAllIds();
                	for (int i = 0; i < objs.length; i++) {
                		String id = objs[i].toString();
                		Object obj = scope.get(id, scope);
                    	if (obj != Scriptable.NOT_FOUND && !(obj instanceof Function) ) {
                    		result.put(id, result, obj);
                    	}
                	}
                	return result;
                	
                } finally {
                    if (ScriptableObject.hasProperty(scope, "close")) {
                        scope.call(cx, "close", args);
                    }
                }
            } catch (final FileNotFoundException fnfe) {
            	throw fnfe;
            }
        } catch (final InstantiationException ie) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ie);
            throw ise;
        } catch (final IllegalAccessException iae) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(iae);
            throw ise;
        } catch (final IOException ioe) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ioe);
            throw ise;
        } catch (final ClassNotFoundException cnfe) {
            final FileNotFoundException fnfe = new FileNotFoundException("Source not found: " + this.getSourceName());
            fnfe.initCause(cnfe);
            throw fnfe;
        }
    }
    
    /**
     * 実行。
     * @param cx JSSP実行コンテキスト
     * @param args 引数
     * @return 生成されたＨＴＭＬソース
     */
    public String execute(final Context cx, final Object[] args) throws JavaScriptException, JavaScriptRedirectError, FileNotFoundException {
        // ページの初期化関数を実行
        try {
            try {
                final ScriptScope scope = this.getScope();
                if (ScriptableObject.hasProperty(scope, "init")) {
                    final ValidationResultSet res = Validation.getInstance().validate(cx, scope, "init");
                    if (res.isValid()) {
                        scope.call(cx, "init", args);
                    } else {
                        Validation.getInstance().callErrorFunction(cx, scope, "init", args, res);
                    }
                }
                try {
                    return this.createView(cx, scope);
                } finally {
                    if (ScriptableObject.hasProperty(scope, "close")) {
                        scope.call(cx, "close", args);
                    }
                }
            } catch (final FileNotFoundException fnfe) {
                // JavaScript は存在しないらしい
                final String path = this.getSourceName();

                // ======================================
                // ！！！ ScriptScopeに対してパスを渡す必要があります ！！！
                // ======================================
                // ∵　.htmlに対応する.jsが「存在する場合」と「存在しない場合」で、
                // 　　.html内で処理されるソースのパスが変わります。
                // 　　（この影響で、.html内の<imart>タグが参照するsource-config.xmlが変わります）
                //
                // 具体的には、、、、
                // 「this.getSourceName() + ".html"」のソース内に<imart type="hoge">が記載されている場合、
                // escapseXmlやescapeJsの設定が、デフォルト設定(=jci.resources.jssp.source.property.xmlの設定)で動作してしまいます。
                //
                // 理由：
                // ・パスを渡さなかった場合（= 引数なしのコンストラクタでScriptScopeのインスタンスを生成した場合）
                // 　→ ScriptScope#getKey()が「Unknown Script」となる。
                // → this.createView(Context, ScriptScope)内で、view スクリプトの実行を行う際、ScriptScope.entry(scope)が行われる。
                // → これにより、現在実行中のスクリプト名が「Unknown Script」となる。（=ScriptScope.current()の返却が「Unknown Script」となります）
                // → <imart type="hoge">でescapseXml属性が設定されていない場合、ScriptScope.current()の値を元に設定ファイルを検索する。
                // 　→ しかし、「Unknown Script」に該当するsource-config.xmlが見つからない。。。
                // 　→ そのため、jci.resources.jssp.source.property.xmlの設定が有効になる（デフォルト設定で動作することになる）
                return this.createView(cx, new ScriptScope(path));
            }
        } catch (final InstantiationException ie) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ie);
            throw ise;
        } catch (final IllegalAccessException iae) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(iae);
            throw ise;
        } catch (final IOException ioe) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ioe);
            throw ise;
        } catch (final ClassNotFoundException cnfe) {
            final FileNotFoundException fnfe = new FileNotFoundException("Source not found: " + this.getSourceName());
            fnfe.initCause(cnfe);
            throw fnfe;
        }
    }
    

    
    /**
     * 実行。
     * @param cx JSSP実行コンテキスト
     * @param args 引数
     * @return 生成されたＨＴＭＬソース
     */
    public String executeWithoutValidation(final Context cx, final Object[] args) throws JavaScriptException, JavaScriptRedirectError, FileNotFoundException {
        // ページの初期化関数を実行
        try {
            try {
                final ScriptScope scope = this.getScope();
                if (ScriptableObject.hasProperty(scope, "init")) {
                    scope.call(cx, "init", args);
                }
                try {
                    return this.createView(cx, scope);
                } finally {
                    if (ScriptableObject.hasProperty(scope, "close")) {
                        scope.call(cx, "close", args);
                    }
                }
            } catch (final FileNotFoundException fnfe) {
                // JavaScript は存在しないらしい
                final String path = this.getSourceName();
                return this.createView(cx, new ScriptScope(path));
            }
        } catch (final InstantiationException ie) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ie);
            throw ise;
        } catch (final IllegalAccessException iae) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(iae);
            throw ise;
        } catch (final IOException ioe) {
            final IllegalStateException ise = new IllegalStateException("JSSP execute error.");
            ise.initCause(ioe);
            throw ise;
        } catch (final ClassNotFoundException cnfe) {
            final FileNotFoundException fnfe = new FileNotFoundException("Source not found: " + this.getSourceName());
            fnfe.initCause(cnfe);
            throw fnfe;
        }
    }

    /**
     * view スクリプトを実行します。
     * @return 作成されたＨＴＭＬソース
     * @throws FileNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String createView(final Context cx, final ScriptScope scope) throws FileNotFoundException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        // view のソースを取得
        final Composition view = this.getComposition();

        // view スクリプトの実行
        final ScriptScope before = ScriptScope.entry(scope);
        try {
            return view.execute(cx, scope);
        } finally {
            ScriptScope.entry(before);
        }
    }

    /**
     * 指定の関数を実行します。
     * @param cx 実行環境
     * @param args 関数の実行時引数
     * @param func 関数名
     * @return 関数の戻り値
     * @throws JavaScriptException 実行エラーが発生した場合にスローされます。
     */
    public Object executeFunction(final Context cx, final Object[] args, final String func) throws JavaScriptException {
        try {
            // 指定関数の実行
            return this.getScope().call(cx, func, args);
        } catch (final FileNotFoundException fnfe) {
            final JavaScriptException jse = new JavaScriptException("JavaScript execute error: " + this.getSourceName(), this.getSourceName(), 0);
            jse.initCause(fnfe);
            throw jse;
        } catch (final InstantiationException ie) {
            final JavaScriptException jse = new JavaScriptException("JavaScript execute error: " + this.getSourceName(), this.getSourceName(), 0);
            jse.initCause(ie);
            throw jse;
        } catch (final IllegalAccessException ie) {
            final JavaScriptException jse = new JavaScriptException("JavaScript execute error: " + this.getSourceName(), this.getSourceName(), 0);
            jse.initCause(ie);
            throw jse;
        } catch (final IOException ie) {
            final JavaScriptException jse = new JavaScriptException("JavaScript execute error: " + this.getSourceName(), this.getSourceName(), 0);
            jse.initCause(ie);
            throw jse;
        } catch (final ClassNotFoundException cnfe) {
            final JavaScriptException jse = new JavaScriptException("JavaScript execute error: " + this.getSourceName(), this.getSourceName(), 0);
            jse.initCause(cnfe);
            throw jse;
        }
    }

    /**************************************************************************
     * 処理コンテンツ名の取得メソッド 【 区分 】Instance Method 【 入力 】なし 【返却値】コンテンツ名（文字列） 【 例外 】なし 【作成者】Hisanari.H 【作成日】02/17,2000 【 概要 】
     **************************************************************************/
    public String getSourceName() {
        return this.key;
    }

    /**************************************************************************
     * 変数スコープの取得メソッド 【 区分 】Instance Method 【 入力 】なし 【返却値】変数スコープ 【 例外 】なし 【作成者】Hisanari.H 【作成日】02/29,2000 【 概要 】
     * @throws FileNotFoundException
     * @throws JavaScriptException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ClassNotFoundException
     **************************************************************************/
    public ScriptScope getScope() throws FileNotFoundException, JavaScriptException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        if (this.scriptScope == null) {
            final JSSPScriptBuilder builder = JSSPScriptBuilder.getBuilder();
            this.scriptScope = builder.getScriptScope(this.getSourceName());
        }
        return this.scriptScope;
    }

    /**
     * view のソースを返します。
     * @return
     * @throws FileNotFoundException
     * @throws JavaScriptException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Composition getComposition() throws FileNotFoundException, JavaScriptException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        if (this.composition == null) {
            // view のソースを取得
            final JSSPViewBuilder builder = JSSPViewBuilder.getBuilder();
            this.composition = builder.getComposition(this.getSourceName());
        }
        return this.composition;
    }
} // End of Class

/* End of File */
