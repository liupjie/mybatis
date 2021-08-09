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
package org.apache.ibatis.reflection.factory;

import java.util.List;
import java.util.Properties;

/**
 * MyBatis uses an ObjectFactory to create all needed new Objects.
 *
 * @author Clinton Begin
 *
 * 对象工厂
 */
public interface ObjectFactory {

    /**
     * 设置工厂属性
     *
     * Sets configuration properties.
     * @param properties configuration properties
     */
    default void setProperties(Properties properties) {
        // NOP
    }

    /**
     * 创建对象实例，根据类型实例
     *
     * Creates a new object with default constructor.
     * @param type Object type
     * @return
     */
    <T> T create(Class<T> type);

    /**
     * 创建对象实例，根据构造器参数类型列表与构造器参数列表
     * Creates a new object with the specified constructor and params.
     * @param type Object type
     * @param constructorArgTypes Constructor argument types
     * @param constructorArgs Constructor argument values
     * @return
     */
    <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);

    /**
     * 判断是否为集合类型
     *
     * Returns true if this object can have a set of other objects.
     * It's main purpose is to support non-java.util.Collection objects like Scala collections.
     *
     * @param type Object type
     * @return whether it is a collection or not
     * @since 3.1.0
     */
    <T> boolean isCollection(Class<T> type);

}
