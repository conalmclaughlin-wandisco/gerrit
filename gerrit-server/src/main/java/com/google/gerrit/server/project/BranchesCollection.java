// Copyright (C) 2013 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.server.project;

import com.google.gerrit.extensions.registration.DynamicMap;
import com.google.gerrit.extensions.restapi.ChildCollection;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gerrit.extensions.restapi.ResourceNotFoundException;
import com.google.gerrit.extensions.restapi.RestView;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class BranchesCollection implements
    ChildCollection<ProjectResource, BranchResource> {
  private final DynamicMap<RestView<BranchResource>> views;
  private final Provider<ListBranches> list;

  @Inject
  BranchesCollection(DynamicMap<RestView<BranchResource>> views,
      Provider<ListBranches> list) {
    this.views = views;
    this.list = list;
  }

  @Override
  public RestView<ProjectResource> list() {
    return list.get();
  }

  @Override
  public BranchResource parse(ProjectResource parent, IdString id)
      throws ResourceNotFoundException {
    throw new ResourceNotFoundException(id);
  }

  @Override
  public DynamicMap<RestView<BranchResource>> views() {
    return views;
  }
}
