/**
 * Copyright ${license.git.copyrightYears} the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.parsing;

/**
 * 通用的占位符解析器，类中有唯一的一个 parse 方法，该方法主要完成占位符的定位工作，
 * 然后把占位符的替换工作交给与其关联的 TokenHandler 处理（从variables中取出对应变量的值返回）
 *
 * @author Clinton Begin
 */
public class GenericTokenParser {

    // 占位符的起始标志
    private final String openToken;
    // 占位符的结束标志
    private final String closeToken;
    // 占位符处理器
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    /**
     * 该方法主要 完成占位符的定位工作，然后把占位符的替换工作交给与其关联的 TokenHandler 处理
     * @param text
     * @return
     */
    public String parse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        // search open token
        // 查找openToken的位置
        int start = text.indexOf(openToken);
        if (start == -1) {
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        // 当存在openToken时，才继续处理
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                // 拼接从0到openToken之前的字符
                builder.append(src, offset, start - offset);
                // 设置offset值为openToken结束的位置
                offset = start + openToken.length();
                // 从offset值之后开始找第一个closeToken的位置
                int end = text.indexOf(closeToken, offset);
                // 如果存在，则继续处理
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        // 继续查找当前closeToken之后的closeToken
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                // 如果不存在
                if (end == -1) {
                    // close token was not found.
                    // 拼接剩余的字符
                    builder.append(src, start, src.length - start);
                    // 设置offset为字符数组的长度
                    offset = src.length;
                } else {
                    /**
                     * DynamicCheckerTokenParser：如果存在，则设置当前SQL为动态的
                     * BindingTokenParser：获取$变量的值
                     * ParameterMappingTokenHandler：将#替换为？，并构建参数映射ParameterMapping
                     */
                    builder.append(handler.handleToken(expression.toString()));
                    // 设置offset值为closeToken结束的位置
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf(openToken, offset);
        }
        // 拼接剩余的字符
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }
}
