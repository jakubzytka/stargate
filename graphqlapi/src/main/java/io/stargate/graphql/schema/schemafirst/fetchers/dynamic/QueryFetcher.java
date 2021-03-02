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
package io.stargate.graphql.schema.schemafirst.fetchers.dynamic;

import graphql.schema.DataFetchingEnvironment;
import io.stargate.auth.AuthenticationService;
import io.stargate.auth.AuthenticationSubject;
import io.stargate.auth.AuthorizationService;
import io.stargate.auth.UnauthorizedException;
import io.stargate.db.datastore.DataStore;
import io.stargate.db.datastore.DataStoreFactory;
import io.stargate.db.schema.Keyspace;
import io.stargate.graphql.schema.schemafirst.processor.MappingModel;
import io.stargate.graphql.schema.schemafirst.processor.QueryModel;

public class QueryFetcher extends DynamicFetcher<Object> {

  private final QueryModel model;

  public QueryFetcher(
      QueryModel model,
      MappingModel mappingModel,
      AuthenticationService authenticationService,
      AuthorizationService authorizationService,
      DataStoreFactory dataStoreFactory) {
    super(mappingModel, authenticationService, authorizationService, dataStoreFactory);
    this.model = model;
  }

  @Override
  protected Object get(
      DataFetchingEnvironment environment,
      DataStore dataStore,
      AuthenticationSubject authenticationSubject)
      throws UnauthorizedException {
    Keyspace keyspace = dataStore.schema().keyspace(model.getEntity().getKeyspaceName());

    if (model.returnsList()) {
      return queryListOfEntities(
          model.getEntity(),
          environment.getArguments(),
          model.getInputNames(),
          dataStore,
          keyspace,
          authenticationSubject);
    } else {
      return querySingleEntity(
          model.getEntity(),
          environment.getArguments(),
          model.getInputNames(),
          dataStore,
          keyspace,
          authenticationSubject);
    }
  }
}
