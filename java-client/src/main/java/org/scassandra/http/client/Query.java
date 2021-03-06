/*
 * Copyright (C) 2014 Christopher Batey
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
package org.scassandra.http.client;

import org.scassandra.cql.CqlType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Query {
    
    public static QueryBuilder builder() {
        return new QueryBuilder();
    }

    public static QueryBuilder builder(CqlType... variableTypes) {
        return new QueryBuilder(Arrays.asList(variableTypes));
    }

    private final String query;

    private final String consistency;
    private final List<Object> variables;
    private final List<CqlType> variableTypes;
    private Query(String query, String consistency, List<Object> variables, List<CqlType> variableTypes) {
        this.query = query;
        this.consistency = consistency;
        this.variables = variables;
        this.variableTypes = variableTypes;
    }

    public String getQuery() {
        return query;
    }

    public String getConsistency() {
        return consistency;
    }

    public List<Object> getVariables() {
        return variables;
    }

    public List<CqlType> getVariableTypes() {
        return variableTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Query query1 = (Query) o;

        if (query != null ? !query.equals(query1.query) : query1.query != null) return false;
        if (consistency != null ? !consistency.equals(query1.consistency) : query1.consistency != null) return false;
        if (variables != null ? !variables.equals(query1.variables) : query1.variables != null) return false;
        return !(variableTypes != null ? !variableTypes.equals(query1.variableTypes) : query1.variableTypes != null);

    }

    @Override
    public int hashCode() {
        int result = query != null ? query.hashCode() : 0;
        result = 31 * result + (consistency != null ? consistency.hashCode() : 0);
        result = 31 * result + (variables != null ? variables.hashCode() : 0);
        result = 31 * result + (variableTypes != null ? variableTypes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Query{" +
                "query='" + query + '\'' +
                ", consistency='" + consistency + '\'' +
                ", variables=" + variables +
                ", variableTypes=" + variableTypes +
                '}';
    }

    public static class QueryBuilder {

        private String query;
        private String consistency = "ONE";
        private List<Object> variables = Collections.emptyList();
        private List<CqlType> variableTypes = Collections.emptyList();

        private QueryBuilder() {}

        private QueryBuilder(List<CqlType> variableTypes) {
            this.variableTypes = variableTypes;
        }

        public QueryBuilder withQuery(String query){
            this.query = query;
            return this;
        }

        /**
         * Defaults to ONE if not set.
         * @param consistency Query consistency
         * @return this builder
         */
        public QueryBuilder withConsistency(String consistency){
            this.consistency = consistency;
            return this;
        }

        public QueryBuilder withVariables(List<Object> variables) {
            this.variables = variables;
            return this;
        }

        public QueryBuilder withVariables(Object... variables) {
            this.variables = Arrays.asList(variables);
            return this;
        }

        public Query build(){
            if (query == null) {
                throw new IllegalStateException("Must set query");
            }
            return new Query(query, consistency, variables, variableTypes);
        }
    }
}
