// Copyright (C) 2009 The Android Open Source Project
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

package com.google.gerrit.sshd.commands;

import com.google.gerrit.sshd.CommandModule;
import com.google.gerrit.sshd.CommandName;
import com.google.gerrit.sshd.Commands;


/** Register the commands a Gerrit server in master mode supports. */
public class MasterCommandModule extends CommandModule {
  @Override
  protected void configure() {
    final CommandName gerrit = Commands.named("gerrit");

    command(gerrit, "approve").to(ApproveCommand.class);
    command(gerrit, "create-account").to(AdminCreateAccount.class);
    command(gerrit, "create-project").to(CreateProject.class);
    command(gerrit, "gsql").to(AdminQueryShell.class);
    command(gerrit, "receive-pack").to(Receive.class);
    command(gerrit, "replicate").to(AdminReplicate.class);
  }
}
