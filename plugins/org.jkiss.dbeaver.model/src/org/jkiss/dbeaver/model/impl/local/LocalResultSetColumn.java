/*
 * DBeaver - Universal Database Manager
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

package org.jkiss.dbeaver.model.impl.local;

import org.jkiss.code.NotNull;
import org.jkiss.code.Nullable;
import org.jkiss.dbeaver.model.DBPDataKind;
import org.jkiss.dbeaver.model.DBUtils;
import org.jkiss.dbeaver.model.exec.DBCAttributeMetaData;
import org.jkiss.dbeaver.model.exec.DBCEntityMetaData;
import org.jkiss.dbeaver.model.exec.DBCResultSet;
import org.jkiss.dbeaver.model.meta.Property;
import org.jkiss.dbeaver.model.struct.DBSTypedObject;

/**
 * LocalResultSetColumn
 */
public class LocalResultSetColumn implements DBCAttributeMetaData
{
    protected final transient DBCResultSet resultSet;
    private final int index;
    private final String label;
    private final DBPDataKind dataKind;
    private final transient DBSTypedObject typedObject;

    public LocalResultSetColumn(DBCResultSet resultSet, int index, String label, DBPDataKind dataKind)
    {
        this.resultSet = resultSet;
        this.index = index;
        this.label = label;
        this.dataKind = dataKind;
        this.typedObject = null;
    }

    public LocalResultSetColumn(DBCResultSet resultSet, int index, String label, DBSTypedObject typedObject)
    {
        this.resultSet = resultSet;
        this.index = index;
        this.label = label;
        this.dataKind = typedObject.getDataKind();
        this.typedObject = typedObject;
    }

    @Property(viewable = true, order = 1)
    @Override
    public int getOrdinalPosition()
    {
        return index;
    }

    @Nullable
    @Override
    public Object getSource() {
        return null;
    }

    @Property(viewable = true, order = 2)
    @NotNull
    @Override
    public String getLabel()
    {
        return label;
    }

    @Property(viewable = true, order = 3)
    @Nullable
    @Override
    public String getEntityName()
    {
        return null;
    }

    @Override
    public boolean isReadOnly()
    {
        return true;
    }

    @Nullable
    @Override
    public DBCEntityMetaData getEntityMetaData()
    {
        return null;
    }

    @Override
    public boolean isRequired()
    {
        return false;
    }

    @Property(viewable = true, order = 4)
    @Override
    public boolean isAutoGenerated() {
        return false;
    }

    @NotNull
    @Override
    public String getName()
    {
        return label;
    }

    @NotNull
    @Property(viewable = true, order = 5)
    @Override
    public String getTypeName()
    {
        return typedObject == null ?
            DBUtils.getDefaultDataTypeName(resultSet.getSession().getDataSource(), dataKind) :
            typedObject.getTypeName();
    }

    @NotNull
    @Override
    public String getFullTypeName() {
        return typedObject == null ? DBUtils.getFullTypeName(this) : typedObject.getFullTypeName();
    }

    @Override
    public int getTypeID()
    {
        return typedObject == null ? 0 : typedObject.getTypeID();
    }

    @NotNull
    @Override
    public DBPDataKind getDataKind()
    {
        return dataKind;
    }

    @Nullable
    @Override
    public Integer getScale()
    {
        return typedObject == null ? null : typedObject.getScale();
    }

    @Nullable
    @Override
    public Integer getPrecision()
    {
        return typedObject == null ? null : typedObject.getPrecision();
    }

    @Override
    public long getMaxLength()
    {
        return typedObject == null ? 0 : typedObject.getMaxLength();
    }

    @Override
    public long getTypeModifiers() {
        return typedObject == null ? 0 : typedObject.getTypeModifiers();
    }

    @Override
    public String toString() {
        return getName();
    }
}
