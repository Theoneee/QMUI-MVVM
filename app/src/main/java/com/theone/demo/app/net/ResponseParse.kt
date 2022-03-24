package the.one.brand.net

import com.theone.demo.app.net.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.GsonUtil
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 * @author The one
 * @date 2021/2/18 0018
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

@Parser(name = "Response")
open class ResponseParse<T> : TypeParser<T> {

    protected constructor() : super()

    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        //第一步，解析code、msg字段，把data当成String对象
        val data: Response<String> = response.convertTo(Response::class, String::class.java)
        var t: T? = null
        if (data.errorCode == 0) {
            //第二步，取出data字段，转换成我们想要的对象
            t = GsonUtil.getObject<T>(data.data, types[0])
        }
        if (t == null && types[0] === String::class.java) {
            //判断我们传入的泛型是String对象，就给t赋值""字符串，确保t不为null
            t = data.errorMsg as T
        }
        if (data.errorCode != 0 || t == null) {
            throw ParseException(data.errorCode.toString(), data.errorMsg, response)
        }
        return t
    }

}