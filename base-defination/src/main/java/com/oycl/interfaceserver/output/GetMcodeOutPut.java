package com.oycl.interfaceserver.output;

import com.oycl.base.BaseOutput;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetMcodeOutPut extends BaseOutput {

    private List<McodeEntity> McodeEntity;


    @Getter
    @Setter
    public static class McodeEntity{
        /**大分类*/
        private String classCd;
        /**小分类*/
        private String itemCd;
        /**名称*/
        private String itemValue;
    }
}


