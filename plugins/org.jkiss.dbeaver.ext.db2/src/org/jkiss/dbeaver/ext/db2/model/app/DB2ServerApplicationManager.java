/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2013-2015 Denis Forveille (titou10.titou10@gmail.com)
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.db2.model.app;

import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.ext.db2.DB2Utils;
import org.jkiss.dbeaver.ext.db2.model.DB2DataSource;
import org.jkiss.dbeaver.model.DBPDataSource;
import org.jkiss.dbeaver.model.admin.sessions.DBAServerSessionManager;
import org.jkiss.dbeaver.model.admin.sessions.DBAServerSessionManagerSQL;
import org.jkiss.dbeaver.model.exec.DBCSession;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCSession;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * DB2 Application Manager
 * 
 * @author Denis Forveille
 */
public class DB2ServerApplicationManager implements DBAServerSessionManager<DB2ServerApplication>, DBAServerSessionManagerSQL {

    private static final String FORCE_APP_CMD = "FORCE APPLICATION (%d)";

    private final DB2DataSource dataSource;

    public DB2ServerApplicationManager(DB2DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @NotNull
    @Override
    public DBPDataSource getDataSource()
    {
        return dataSource;
    }

    @NotNull
    @Override
    public Collection<DB2ServerApplication> getSessions(@NotNull DBCSession session, @NotNull Map<String, Object> options) throws DBException
    {
        try {
            return DB2Utils.readApplications(session.getProgressMonitor(), (JDBCSession) session);
        } catch (SQLException e) {
            throw new DBException(e, session.getDataSource());
        }
    }

    @Override
    public void alterSession(@NotNull DBCSession session, @NotNull DB2ServerApplication sessionType, @NotNull Map<String, Object> options) throws DBException
    {
        try {
            String cmd = String.format(FORCE_APP_CMD, sessionType.getAgentId());
            DB2Utils.callAdminCmd(session.getProgressMonitor(), dataSource, cmd);
        } catch (SQLException e) {
            throw new DBException(e, session.getDataSource());
        }
    }

    @Override
    public boolean canGenerateSessionReadQuery() {
        return true;
    }

    @NotNull
    @Override
    public String generateSessionReadQuery(@NotNull Map<String, Object> options) {
        return DB2Utils.SEL_APP;
    }
}
