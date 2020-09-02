package cn.yumetsuki.lexical_analysis

sealed class Token(
    val type: String,
    val value: String
)

// 标识符 -> 不以数字开头，无空格/问号/百分号等特殊符号
class Identifier(value: String) : Token("identifier", value)
// 赋值 -> =
class Assignment(value: String) : Token("assignment", value)
// 数字 -> int、double
class Number(value: String) : Token("number", value)
// 加号 -> +
class Plus(value: String) : Token("plus", value)
// 减号 -> -
class Minus(value: String) : Token("minus", value)
// 乘号 -> *
class Multiplication(value: String) : Token("multiplication", value)
// 除号 -> /
class Division(value: String) : Token("division", value)
// 取余 -> %
class Mod(value: String) : Token("mod", value)
// 左括号 -> (
class LeftParentheses(value: String) : Token("left_parentheses", value)
// 右括号 -> )
class RightParentheses(value: String) : Token("right_parentheses", value)