package com.bosssoft.egov.asset.activiti.modeler.rest.editor.main;

/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Tijs Rademakers
 */
@Controller
public class StencilsetRestResource {
	
	@RequestMapping(value = "/act/service/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public void getStencilset(HttpServletResponse response) {
		InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
		try {
			 response.setContentType("text/html;charset=UTF-8");
			 response.getOutputStream().write(IOUtils.toString(stencilsetStream, "utf-8").getBytes());
		} catch (Exception e) {
			throw new ActivitiException("Error while loading stencil set", e);
		}
	}
}
