/**
 * JSON 関連のメソッドを提供します。
 * @fileoverview JSON 関連のライブラリです。
 * @class JSON 関連のライブラリです。<br/>
 * 		  <br/>
 * 		  JSON (JavaScript Object Notation)は、軽量のデータ交換フォーマットです。<br/>
 * 		  JSON については、<a href="http://www.json.org/json-ja.html">JSON の紹介</a> を参照してください。
 * @constructor
 *
 * @version 1.0
 */
function NdimsJson(){}

/**
 * JSON 文字列からJavaScriptオブジェクトへの変換に失敗した場合に投げられる例外のメッセージです。
 * @final
 * @type String
 */
NdimsJson.PARSE_JSON_ERROR_MESSAGE = "parseJSON Error";

/**
 * @private
 * インデント用文字列のキャッシュ
 */
NdimsJson.indentStringCache = new Array();

/**
 * インデント文字列
 * @final
 * @type String
 */
NdimsJson.INDENT_STRING = "    ";

/**
 * @private
 * Name/Valueのセパレータ文字列
 * @final
 * @type String
 */
NdimsJson.NAME_VALUE_SEPARATER = ", ";

/**
 * 「null」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_NULL = "Null";

/**
 * 「Undefined」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_UNDEFINED = "Undefined";

/**
 * 「String」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_STRING = "String";

/**
 * 「Date」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_DATE = "Date";

/**
 * 「Array」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_ARRAY = "Array";

/**
 * 「Object」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_OBJECT = "Object";

/**
 * 「Function」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_FUNCTION = "Function";

/**
 * 「Number」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_NUMBER = "Number";

/**
 * 「BigDecimal」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_BIG_DECIMAL = "BigDecimal";

/**
 * 「BigInteger」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_BIG_INTEGER = "BigInteger";

/**
 * 「Boolean」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_BOOLEAN = "Boolean";

/**
 * 「TimeZone」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_TIME_ZONE = "TimeZone";

/**
 * 「DateTime」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_DATE_TIME = "DateTime";

/**
 * 「XML」型 を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_XML = "XML";

/**
 * 型が特定できない場合を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_UNKNOWN = "Unknown";

/**
 * 「Java」型を表す定数
 * @final
 * @type String
 */
NdimsJson.TYPE_JAVA = "Java";

/**
 * JSON 文字列からJavaScriptオブジェクトに変換します。<br/>
 * <br/>
 *
 * @param {String} jsonString JSON 文字列
 * @return JavaScriptオブジェクト
 * @type Object
 * @throws SyntaxError JSON 文字列からJavaScriptオブジェクトへの変換に失敗した場合に投げられます。
 *						（例外のメッセージは、{@link NdimsJson#PARSE_JSON_ERROR_MESSAGE}に設定されます）
 */
NdimsJson.parseJSON = function(jsonString) {

    if(false == NdimsJson.checkJSONString(jsonString)){
        throw new SyntaxError(NdimsJson.PARSE_JSON_ERROR_MESSAGE);
    }

    try{
        var newObject;

        newObject = eval('('+jsonString+')');
        return newObject;
    } catch(e){
        throw new SyntaxError(NdimsJson.PARSE_JSON_ERROR_MESSAGE);
    }
}

/**
 * JSON 文字列に変換します。<br/>
 * <br/>
 * 引数「debugFlg」が true の場合、JSON 文字列のインデント化、および、型名の付与を行います。<br/>
 * その際、変換対象オブジェクト（内部のプロパティも含む）が 以下の型の場合、特別な動作をします。<br/>
 *
 * <br/>
 * <table border="1">
 * 	<tr>
 * 		<td bgcolor="lightgrey">Date 型</td>
 * 		<td>型名の右側に、日付の文字列表現がJavaScriptのコメントとして出力されます。</td>
 * 	</tr>
 * 	<tr>
 * 		<td bgcolor="lightgrey">Function 型</td>
 * 		<td><b>"THIS_IS_FUNCTION"</b> として表現します。</td>
 * 	</tr>
 * </table>
 * <br/>
 * なお、引数「debugFlg」が true 時の本メソッドの返却値には型名が付与されているため、<br/>
 * {@link NdimsJson#checkJSONString}でのチェックには失敗します。<br/>
 * <br/>
 * 変換対象オブジェクト（内部のプロパティも含む）がBigInteger型もしくはBigDecimal型である場合、<br/>
 * plainStringプロパティを利用して文字列に変換します。このとき、型情報が失われるため、<br/>
 * 変換後のJSON文字列を{@link NdimsJson#parseJSON parseJSON()}で再度JavaScriptオブジェクトに変換しても、元の型に戻すことはできません。<br/>
 * @param {Object} value 変換対象
 * @param {Boolean} debugFlg [option] JSON 文字列のインデント化、および、型名の付与を行う場合は trueを設定してください。
 * 									  省略時のデフォルトは false。
 * @return JSON 文字列
 * @type String
 */
NdimsJson.toJSONString = function(value, debugFlg){

    /**
     * @private
     * 引数で与えられた値の型を判定します。
     *
     * @param {Object} value 判定対象オブジェクト
     * @return 型を表す文字列
     * @type String
     */
    function getTypeName(value){
        if( isJavaInstance(value) ){
            return NdimsJson.TYPE_JAVA;
        }

        if( value === null ) {
            return NdimsJson.TYPE_NULL;
        }
        else if( typeof(value) == "undefined" ) {
            return null;
        }
        else if( typeof(value) == "string" ) {
            return NdimsJson.TYPE_STRING;
        }
        else if( typeof(value) == "number" ) {
            return NdimsJson.TYPE_NUMBER;
        }
        else if( typeof(value) == "object" && typeof(value.toPlainString) == "function") {
            return NdimsJson.TYPE_BIG_DECIMAL;
        }
        else if( typeof(value) == "object" && typeof(value.multiply) == "function") {
            return NdimsJson.TYPE_BIG_INTEGER;
        }
        else if( typeof(value) == "boolean" ) {
            return NdimsJson.TYPE_BOOLEAN;
        }
        else if( typeof(value) == "object" && typeof(value.push) == "function" ) {
            return NdimsJson.TYPE_ARRAY;
        }
        else if( typeof(value) == "object" && typeof(value.getFullYear) == "function" ) {
            return NdimsJson.TYPE_DATE;
        }
        else if( typeof(value) == "object" && typeof(value.getOffset) == "function") {
            return NdimsJson.TYPE_TIME_ZONE;
        }
        else if( typeof(value) == "object" && typeof(value.withYear) == "function" && typeof(value.plusYears) == "function") {
            return NdimsJson.TYPE_DATE_TIME;
        }
        else if( typeof(value) == "xml" ) {
            return NdimsJson.TYPE_XML;
        }
        else if( typeof(value) == "function" ) {
            return NdimsJson.TYPE_FUNCTION;
        }
        else if( typeof(value) == "object" ) {
            return NdimsJson.TYPE_OBJECT;
        }
        else {
            return NdimsJson.TYPE_UNKNOWN;
        }
    }


    /**
     * @private
     * JSON 文字列への変換（Object用）
     *
     * @param {Object} objectData 変換対象オブジェクト
     * @param {String} indent インデント
     * @return JSON 文字列
     * @type String
     */
    function toJSONString4Object(objectData, indentDepth){
        var array = new Array();

        for(var prop in objectData){
            if( typeof(objectData.hasOwnProperty) != "function"
                ||
                objectData.hasOwnProperty(prop)) {

                var tempStr  = '"' + NdimsJson.escapeData(prop) + '" : ';
                var typeName = getTypeName(objectData[prop]);

                switch(typeName){
                    case NdimsJson.TYPE_NULL :
                    case NdimsJson.TYPE_UNDEFINED :
                        if(debugFlg == true){
                            typeName += getJavaClassName(objectData, prop); // Javaクラス名があれば追記
                        }
                        tempStr += new String(objectData[prop]);
                        break;

                    case NdimsJson.TYPE_NUMBER :
                    case NdimsJson.TYPE_BOOLEAN :
                        tempStr += objectData[prop];
                        break;
                        
                    case NdimsJson.TYPE_BIG_DECIMAL :
                        tempStr += ("\"" + objectData[prop].toPlainString() + "\"");
                        break;

                    case NdimsJson.TYPE_BIG_INTEGER :
                        tempStr += ("\"" + objectData[prop].toString() + "\"");
                        break;

                    case NdimsJson.TYPE_DATE :
                        if(debugFlg == true){
                            typeName += " (" + objectData[prop].toString() + ")";  /* ← 型名の横にDateの文字列表現 */
                        }
                        tempStr += NdimsJson.dateToSrcString(objectData[prop]);
                        break;

                    case NdimsJson.TYPE_ARRAY :
                        if(debugFlg == true){
                            typeName += getJavaClassName(objectData, prop); // Javaクラス名があれば追記
                        }
                        tempStr += toJSONString4Array(objectData[prop], indentDepth + 1);
                        break;

                    case NdimsJson.TYPE_OBJECT :
                        if(debugFlg == true){
                            typeName += getJavaClassName(objectData, prop); // Javaクラス名があれば追記
                        }
                        tempStr += toJSONString4Object(objectData[prop], indentDepth + 1);
                        break;

                    case NdimsJson.TYPE_FUNCTION :
                        /* FunctionはJSON 文字列化しない */
                        if(debugFlg == true){
                            tempStr += '\"THIS_IS_FUNCTION\"';
                            array[array.length] = concatTypeName(tempStr, typeName, indentDepth);
                        }
                        continue;

                    case NdimsJson.TYPE_TIME_ZONE :
                        if(debugFlg == true){
                            typeName += getJavaClassName(objectData, prop);
                        }
                        tempStr += toJSONString4Object(objectData[prop], indentDepth + 1);
                        break;

                    case NdimsJson.TYPE_DATE_TIME :
                        if(debugFlg == true){
                            typeName += " (" + objectData[prop].toString() + ")";
                        }
                        tempStr += toJSONString4Object(objectData[prop], indentDepth + 1);
                        break;

                    case NdimsJson.TYPE_XML :
                        tempStr += '"\n' + objectData[prop].toXMLString() + '"';
                        break;

                    case NdimsJson.TYPE_JAVA :
                        if(debugFlg == true){
                            typeName += getJavaClassName(objectData, prop); // Javaクラス名があれば追記
                        }
                        tempStr += '"' + NdimsJson.escapeData( new String(objectData[prop]) ) + '"';
                        break;

                    case NdimsJson.TYPE_STRING :
                    case NdimsJson.TYPE_UNKNOWN :
                    default :
                        tempStr += '"' + NdimsJson.escapeData( new String(objectData[prop]) ) + '"';
                        break;
                }

                /* 型の名称を付与 */
                if(debugFlg == true){
                    tempStr = concatTypeName(tempStr, typeName, indentDepth);
                }

                array[array.length] = tempStr;
            }
        }

        var str = "{";

        if(debugFlg == true){
            str += concatIndent(array, indentDepth);
        }
        else {
            str += array.join(NdimsJson.NAME_VALUE_SEPARATER);
        }

        str += "}";
        return str;
    }

    /**
     * @private
     * JSON 文字列への変換（Array用）<br/>
     *
     * @param {Array} arrayData 配列
     * @param {Number} indentDepth インデントの深さ
     * @return JSON 文字列
     * @type String
     */
    function toJSONString4Array(arrayData, indentDepth){
        var array = new Array();

        for(var idx = 0, max = arrayData.length;  idx < max; idx++){
            var typeName = getTypeName(arrayData[idx]);
            var insertIdx = array.length;

            switch(typeName){
                case NdimsJson.TYPE_NULL :
                case NdimsJson.TYPE_UNDEFINED :
                    if(debugFlg == true){
                        typeName += getJavaClassName(arrayData, ""); // Javaクラス名があれば追記
                    }
                    array[insertIdx] = new String(arrayData[idx]);
                    break;

                case NdimsJson.TYPE_NUMBER :
                case NdimsJson.TYPE_BOOLEAN :
                    array[insertIdx] = arrayData[idx];
                    break;
                    
                case NdimsJson.TYPE_BIG_DECIMAL :
                    array[insertIdx] = "\"" + objectData[prop].toPlainString() + "\"";
                    break;

                case NdimsJson.TYPE_BIG_INTEGER :
                    array[insertIdx] = "\"" + objectData[prop].toString() + "\"";
                    break;

                case NdimsJson.TYPE_DATE :
                    if(debugFlg == true){
                        typeName += " (" + arrayData[idx].toString() + ")";  /* ← 型名の横にDateの文字列表現 */
                    }
                    array[insertIdx] = NdimsJson.dateToSrcString(arrayData[idx]);
                    break;

                case NdimsJson.TYPE_ARRAY :
                    if(debugFlg == true){
                        typeName += getJavaClassName(arrayData, ""); // Javaクラス名があれば追記
                    }
                    array[insertIdx] = toJSONString4Array(arrayData[idx], indentDepth + 1);
                    break;

                case NdimsJson.TYPE_OBJECT :
                    if(debugFlg == true){
                        typeName += getJavaClassName(arrayData, ""); // Javaクラス名があれば追記
                    }
                    array[insertIdx] = toJSONString4Object(arrayData[idx], indentDepth + 1);
                    break;

                case NdimsJson.TYPE_FUNCTION :
                    /* FunctionはJSON 文字列化しない */
                    if(debugFlg == true){
                        array[insertIdx] = concatTypeName('\"THIS_IS_FUNCTION\"', typeName, indentDepth);
                    }
                    continue;

                case NdimsJson.TYPE_TIME_ZONE :
                    if(debugFlg == true){
                        typeName += getJavaClassName(arrayData, "");
                    }
                    array[insertIdx] = toJSONString4Object(arrayData[idx], indentDepth + 1);
                    break;

                case NdimsJson.TYPE_DATE_TIME :
                    if(debugFlg == true){
                        typeName += " (" + arrayData[idx].toString() + ")";  /* ← 型名の横にDateTimeの文字列表現 */
                    }
                    array[insertIdx] = toJSONString4Object(arrayData[idx], indentDepth + 1);
                    break;

                case NdimsJson.TYPE_XML :
                    array[insertIdx] = '"\n' + arrayData[idx].toXMLString() + '"';
                    break;

                case NdimsJson.TYPE_JAVA :
                    if(debugFlg == true){
                        typeName += getJavaClassName(arrayData, ""); // Javaクラス名があれば追記
                    }
                    array[insertIdx] = '"' + NdimsJson.escapeData( new String(arrayData[idx]) ) + '"';
                    break;

                case NdimsJson.TYPE_STRING :
                case NdimsJson.TYPE_UNKNOWN :
                default :
                    array[insertIdx] = '"' + NdimsJson.escapeData( new String(arrayData[idx]) ) + '"';
                    break;
            }

            /* 型の名称を付与 */
            if(debugFlg == true){
                array[insertIdx] = concatTypeName(array[insertIdx], typeName, indentDepth);
            }
        }

        var str = "[";

        if(debugFlg == true){
            str += concatIndent(array, indentDepth);
        }
        else {
            str += array.join(NdimsJson.NAME_VALUE_SEPARATER);
        }

        str += "]";
        return str;
    }

    /**
     * @private
     * インデント文字列を 引数「depth」個分連結した文字列返却します。<br/>
     * <br/>
     *
     * @param {Number} depth インデントの深さ
     * @return インデント文字列
     * @type String
     */
    function getIndentString(depth){
        var indentString = NdimsJson.indentStringCache[depth];

        if(!indentString){
            indentString = "";
            for(var idx = 0; idx < depth; idx++){
                indentString += NdimsJson.INDENT_STRING;
            }
            NdimsJson.indentStringCache[depth] = indentString;
        }
        return indentString;
    }

    /**
     * @private
     * 引数「value」に型の名称を付加した文字列を返却します。<br/>
     * <br/>
     * 引数「depth」で指定された値を元に、インデント処理を行います。
     *
     * @param {String} value 文字列
     * @param {String} typeName 型の名称
     * @param {Number} depth インデントの深さ
     * @return 引数「value」に型の名称を付加した文字列
     * @type String
     */
    function concatTypeName(value, typeName, depth){
        depth = (depth) ? depth : 0;
        return "/* " + typeName + " */" + "\n" + getIndentString(depth) + value;
    }

    /**
     * @private
     * Name/Valueのセパレータ文字列で配列を連結します。<br/>
     * <br/>
     * 引数「depth」で指定された値を元に、インデント処理を行います。
     *
     * @param {Array} array 連結を行う配列
     * @param {Number} depth インデントの深さ
     * @return 連結後の文字列
     * @type String
     */
    function concatIndent(array, depth){
        var str = "";

        str += "\n" + getIndentString(depth);
        str += array.join(NdimsJson.NAME_VALUE_SEPARATER + "\n\n" + getIndentString(depth));
        str += "\n" + getIndentString(depth - 1);

        return str;
    }

    /**
     * @private
     * 引数に渡されたJSオブジェクトが、
     * 「org.intra_mart.jssp.util.JavaScriptUtility」クラスを利用して
     * JavaBeanから変換されたJSオブジェクトだった場合、
     * 引数「typeName」に変換元のJavaBeanのクラス名を連結した文字列を返却します。
     *
     * @param {Object} obj JSオブジェクト
     * @param {String} typeName 型の名称
     * @return 引数「typeName」にクラス名を連結した文字列。
     *         変換元がStringクラス等のラッパークラス、または、JavaBeanでは無い場合は引数「typeName」をそのまま返却します。
     * @type String
     */
    function getJavaClassName(obj, propName){
        if(obj == undefined || obj == null){
            return "";
        }

        var javaClassName = obj["__javaClassName_" + propName + "__"];

        if(javaClassName == undefined){
            return "";
        }
        else{
            return " <" + javaClassName + ">";
        }
    }

    /**
     * @private
     * NdimsJsonのtoJSONString() 処理本体
     */
    {
        debugFlg = (debugFlg) ? debugFlg : false;

        var jsonString = "";
        var typeName = getTypeName(value);

        switch(typeName){
            case NdimsJson.TYPE_NULL :
            case NdimsJson.TYPE_UNDEFINED :
                if(debugFlg == true){
                    typeName += getJavaClassName(value, ""); // Javaクラス名があれば追記
                }
                jsonString = new String(value);
                break;

            case NdimsJson.TYPE_NUMBER :
            case NdimsJson.TYPE_BOOLEAN :
                jsonString = value;
                break;

            case NdimsJson.TYPE_BIG_DECIMAL :
                jsonString = "\"" + value.toPlainString() + "\"";
                break;

            case NdimsJson.TYPE_BIG_INTEGER :
                jsonString = "\"" + value.toString() + "\"";
                break;

            case NdimsJson.TYPE_DATE :
                if(debugFlg == true){
                    typeName += " (" + value.toString() + ")";  /* ← 型名の横にDateの文字列表現 */
                }
                jsonString = NdimsJson.dateToSrcString(value);
                break;

            case NdimsJson.TYPE_ARRAY :
                if(debugFlg == true){
                    typeName += getJavaClassName(value, ""); // Javaクラス名があれば追記
                }
                jsonString = toJSONString4Array(value, 1);
                break;

            case NdimsJson.TYPE_OBJECT :
                if(debugFlg == true){
                    typeName += getJavaClassName(value, ""); // Javaクラス名があれば追記
                }
                jsonString = toJSONString4Object(value, 1);
                break;

            case NdimsJson.TYPE_FUNCTION :
                /* FunctionはJSON 文字列化しない */
                if(debugFlg == true){
                    jsonString = '\"THIS_IS_FUNCTION\"';
                }
                break;

            case NdimsJson.TYPE_TIME_ZONE :
                if(debugFlg == true){
                    typeName += getJavaClassName(value, ""); // Javaクラス名があれば追記
                }
                jsonString = toJSONString4Object(value, 1);
                break;

            case NdimsJson.TYPE_DATE_TIME :
                if(debugFlg == true){
                    typeName += " (" + value.toString() + ")";  /* ← 型名の横にDateTimeの文字列表現 */
                }
                jsonString = toJSONString4Object(value, 1);
                break;

            case NdimsJson.TYPE_XML :
                jsonString = '"\n' + value.toXMLString() + '"';
                break;

            case NdimsJson.TYPE_JAVA :
                if(debugFlg == true){
                    typeName += getJavaClassName(value, ""); // Javaクラス名があれば追記
                }
                jsonString = '"' + NdimsJson.escapeData(new String(value) ) + '"';
                break;

            case NdimsJson.TYPE_STRING :
            case NdimsJson.TYPE_UNKNOWN :
            default :
                jsonString = '"' + NdimsJson.escapeData(new String(value) ) + '"';
                break;
        }

        /* 型の名称を付与 */
        if(debugFlg == true){
            jsonString = concatTypeName(jsonString, typeName, 0);
        }

        return String(jsonString);
    }
}

/**
 * @private
 * 文字列をエスケープします。
 *
 * @param {String} targetData エスケープ対象文字列
 * @return エスケープ後の文字列
 * @type String
 */
NdimsJson.escapeData = function(targetData) {
    var returnData;
    var convTbl = {
        '\b': '\\b',
        '\t': '\\t',
        '\n': '\\n',
        '\f': '\\f',
        '\r': '\\r',
        '"' : '\\"'
    };

    returnData = targetData.replace(/\\/g, "\\\\");
    returnData = returnData.replace(/\"/g, "\\\"");

    returnData = returnData.replace(/([\x08-\x0D])/g,function(pc,nc){
                    var convChar = convTbl[pc];
                    if (convChar) {
                        return convChar;
                    }
                    return pc;
                 })

    /* 改行コードの変換 */
    returnData = returnData.split("\r\n").join("\\n");
    returnData = returnData.split("\r").join("\\r");
    returnData = returnData.split("\n").join("\\n");

    return returnData;
}

/**
 * JSON 文字列の妥当性をチェックします。
 *
 * @param {String} jsonString JSON 文字列
 * @return 正当なJSON 文字列の場合は true、不正なJSON 文字列の場合は false を返却します。
 * @type Boolean
 */
NdimsJson.checkJSONString = function(jsonString){
    if (jsonString == null) {
        return true;
    }

    if (typeof(jsonString) === 'string' || jsonString instanceof String) {
        if(jsonString.length <= 0) {
            return false;
        }

        jsonString = jsonString.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@');
        jsonString = jsonString.replace(/"[^"\\\n\r]*"|true|false|null|new Date\(\-?[0-9]*\)|undefined|NaN|Infinity|\-Infinity|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']');
        jsonString = jsonString.replace(/(?:^|:|,)(?:\s*\[)+/g, '');

        return (/^[\],:{}\s]*$/).test(jsonString);
    } else if (typeof(jsonString) === 'number' || jsonString instanceof Number) {
        return true;
    } else if (typeof(jsonString) === 'boolean' || jsonString instanceof Boolean) {
        return true;
    } else if (jsonString instanceof Array) {
        var length = jsonString.length;

        if (length <= 0) {
            return false;
        }

        for (var i = 0; i < length; ++i) {
            if (jsonString[i] == null || !NdimsJson.checkJSONString(jsonString[i])) {
                return false;
            }
        }

        return true;
    } else {
        return false;
    }
}

/**
 * @private
 * Date型のオブジェクトをJSON 文字列化します。
 *
 * @parama {Date} date Date型のオブジェクト
 * @return Date型オブジェクトを変換したJSON 文字列
 * @type String
 */
NdimsJson.dateToSrcString = function(date) {
	// TODO

//    var src = "new Date(" +
//        date.getTime() + ")";
	var src = "\"" + DateTimeFormatter.format("yyyy/MM/dd HH:mm:ss", date) + "\"";
    return src;
}

/**
 * @private
 * Function : /intra-mart/jssp-rpc/marshller/function-name/marshall
 */
var NdimsJsonMarshall = function (obj, debugFlg) {
    return NdimsJson.toJSONString(obj, debugFlg);
}

/**
 * @private
 * Function : /intra-mart/jssp-rpc/marshller/function-name/unmarshall
 */
var NdimsJsonUnmarshall = function (jsonString) {
    return NdimsJson.parseJSON(jsonString);
}
