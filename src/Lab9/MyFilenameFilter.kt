package Lab9

import java.io.File
import java.io.FilenameFilter
import java.util.*


internal class MyFilenameFilter : FilenameFilter {
    override fun accept(dir: File, name: String): Boolean {
        val st = StringTokenizer(name, ".")
        var token = ""
        while (st.hasMoreTokens()) token = st.nextToken()
        return if (token == "docx") true else false
    }
}