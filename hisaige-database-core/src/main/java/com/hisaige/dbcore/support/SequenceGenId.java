package com.hisaige.dbcore.support;

import tk.mybatis.mapper.genid.GenId;

/**
 * @author chenyj
 * 2020/9/20 - 23:15.
 **/
public class SequenceGenId implements GenId<String> {
    private static final Sequence SEQUENCE = new Sequence();
    @Override
    public String genId(String table, String column) {
        return String.valueOf(SEQUENCE.nextId());
    }
}
