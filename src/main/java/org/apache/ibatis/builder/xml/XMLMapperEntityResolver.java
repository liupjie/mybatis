/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.ibatis.io.Resources;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Offline entity resolver for the MyBatis DTDs.
 *
 * @author Clinton Begin
 * @author Eduardo Macarron
 */

/**
 * XMLMapperEntityResolver的 resolveEntity方法通过字符串匹配 找出了本地的 DTD文档并返回，因此 MyBatis可以在无网络的环境下正 常地校验 XML文件。
 */
public class XMLMapperEntityResolver implements EntityResolver {

  private static final String IBATIS_CONFIG_SYSTEM = "ibatis-3-config.dtd";
  private static final String IBATIS_MAPPER_SYSTEM = "ibatis-3-mapper.dtd";
  private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_SYSTEM = "mybatis-3-mapper.dtd";

  private static final String MYBATIS_CONFIG_DTD = "org/apache/ibatis/builder/xml/mybatis-3-config.dtd";
  private static final String MYBATIS_MAPPER_DTD = "org/apache/ibatis/builder/xml/mybatis-3-mapper.dtd";

  /**
   * Converts a public DTD into a local one.
   *
   * @param publicId The public id that is what comes after "PUBLIC"
   * @param systemId The system id that is what comes after the public id.
   * @return The InputSource for the DTD
   *
   * @throws org.xml.sax.SAXException If anything goes wrong
   */
  /**
   * XMLMapperEntityResolver的 resolveEntity方法通过字符串匹配 找出了本地的 DTD文档并返回，因此 MyBatis可以在无网络的环境下正 常地校验 XML文件
   *
   * 在一个XML文件的头部是这样的：
   * <!DOCTYPE configuration
   *         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
   *         "http://mybatis.org/dtd/mybatis-3-config.dtd">
   *  那么上述例子中，
   * @param publicId 为-//mybatis.org//DTD Config 3.0//EN
   * @param systemId 为http://mybatis.org/dtd/mybatis-3-config.dtd
   * @return 对应DTD文档的输入流
   * @throws SAXException
   */
  @Override
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
    try {
      if (systemId != null) {
        // 将systemId转为全小写
        String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
        if (lowerCaseSystemId.contains(MYBATIS_CONFIG_SYSTEM) || lowerCaseSystemId.contains(IBATIS_CONFIG_SYSTEM)) {
          // 说明这个是配置文档
          // 直接把本地配置文档的dtd文件返回
          return getInputSource(MYBATIS_CONFIG_DTD, publicId, systemId);
        } else if (lowerCaseSystemId.contains(MYBATIS_MAPPER_SYSTEM) || lowerCaseSystemId.contains(IBATIS_MAPPER_SYSTEM)) {
          // 说明这个是映射文档
          // 直接把本地映射文档的dtd文件返回
          return getInputSource(MYBATIS_MAPPER_DTD, publicId, systemId);
        }
      }
      return null;
    } catch (Exception e) {
      throw new SAXException(e.toString());
    }
  }

  // 获取本地的dtd文件，并设置其publicId、systemId
  private InputSource getInputSource(String path, String publicId, String systemId) {
    InputSource source = null;
    if (path != null) {
      try {
        InputStream in = Resources.getResourceAsStream(path);
        source = new InputSource(in);
        source.setPublicId(publicId);
        source.setSystemId(systemId);
      } catch (IOException e) {
        // ignore, null is ok
      }
    }
    return source;
  }

}
