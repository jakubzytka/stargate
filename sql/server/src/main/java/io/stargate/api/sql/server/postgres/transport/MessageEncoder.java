/*
 * Copyright The Stargate Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.stargate.api.sql.server.postgres.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.stargate.api.sql.server.postgres.msg.PGServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEncoder extends MessageToByteEncoder<PGServerMessage> {
  private static final Logger LOG = LoggerFactory.getLogger(MessageEncoder.class);

  @Override
  protected void encode(ChannelHandlerContext ctx, PGServerMessage msg, ByteBuf out) {
    LOG.trace("encode: {}", msg.getClass().getSimpleName());
    msg.write(out);
  }
}
