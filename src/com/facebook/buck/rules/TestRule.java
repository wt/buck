/*
 * Copyright 2012-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.rules;

import com.facebook.buck.step.ExecutionContext;
import com.facebook.buck.step.Step;
import com.facebook.buck.test.TestResults;
import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * A {@link BuildRule} that is designed to run tests.
 */
public interface TestRule extends BuildRule {

  /**
   * Returns a boolean indicating whether the files that contain the test results for this rule are
   * present.
   * <p>
   * If this method returns {@code true}, then {@link #interpretTestResults(ExecutionContext)}
   * should be able to be called directly.
   */
  public boolean hasTestResultFiles(ExecutionContext executionContext);

  /**
   * Returns the commands required to run the tests.
   * <p>
   * <strong>Note:</strong> This method may be run without {@link #build(BuildContext)} having been
   * run. This happens if the user has built [and ran] the test previously and then re-runs it using
   * the {@code --debug} flag.
   * @param buildContext Because this method may be run without {@link #build(BuildContext)} having
   *     been run, this is supplied in case any non-cacheable build work needs to be done.
   * @param executionContext Provides context for creating {@link com.facebook.buck.step.Step}s.
   * @return the commands required to run the tests
   */
  public List<Step> runTests(BuildContext buildContext, ExecutionContext executionContext);

  public Callable<TestResults> interpretTestResults(ExecutionContext executionContext);

  /**
   * @return The set of labels for this build rule.
   */
  public ImmutableSet<String> getLabels();
}
