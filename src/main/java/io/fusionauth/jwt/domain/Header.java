/*
 * Copyright (c) 2016-2019, FusionAuth, All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package io.fusionauth.jwt.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fusionauth.jwt.json.Mapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JSON Object Signing and Encryption (JOSE) Header
 *
 * @author Daniel DeGroff
 */
public class Header {
  @JsonProperty("alg")
  public Algorithm algorithm;

  @JsonIgnore
  public Map<String, String> properties = new LinkedHashMap<>();

  @JsonProperty("typ")
  public Type type = Type.JWT;

  public Header() {
  }

  public Header(Algorithm algorithm) {
    this.algorithm = algorithm;
  }

  /**
   * Special getter used to flatten additional header properties into top level values. Necessary to correctly serialize
   * this object.
   *
   * @return a map of properties to be serialized as if they were actual properties of this class.
   */
  @JsonAnyGetter
  public Map<String, String> anyGetter() {
    return properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Header)) return false;
    Header header = (Header) o;
    return algorithm == header.algorithm &&
        Objects.equals(properties, header.properties) &&
        type == header.type;
  }

  public String get(String name) {
    return properties.get(name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(algorithm, properties, type);
  }

  /**
   * Add a property to the JWT header.
   *
   * @param name  The name of the header property.
   * @param value The value of the header property.
   * @return this.
   */
  @JsonAnySetter
  public Header set(String name, String value) {
    if (name == null || value == null) {
      return this;
    }

    properties.put(name, value);
    return this;
  }

  @Override
  public String toString() {
    return new String(Mapper.prettyPrint(this));
  }
}
