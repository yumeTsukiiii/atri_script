package cn.yumetsuki.semantic_analysis

interface Memory {

    fun getVariable(name: String): Any?

    fun setVariable(name: String, value: Any?)

}

class GlobalMemory : Memory {

    private val variables = hashMapOf<String, Any?>()

    override fun getVariable(name: String): Any? = variables[name]

    override fun setVariable(name: String, value: Any?) {
        variables[name] = value
    }

}