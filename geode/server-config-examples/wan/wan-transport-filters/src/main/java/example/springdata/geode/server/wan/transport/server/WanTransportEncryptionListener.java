/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.server.wan.transport.server;

import org.apache.geode.cache.wan.GatewayTransportFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

@Component
public class WanTransportEncryptionListener implements GatewayTransportFilter {

	private final Adler32 CHECKER = new Adler32();

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public InputStream getInputStream(InputStream stream) {
		logger.info("CheckedTransportFilter: Getting input stream");
		return new CheckedInputStream(stream, CHECKER);
	}

	@Override
	public OutputStream getOutputStream(OutputStream stream) {
		logger.info("CheckedTransportFilter: Getting output stream");
		return new CheckedOutputStream(stream, CHECKER);
	}
}