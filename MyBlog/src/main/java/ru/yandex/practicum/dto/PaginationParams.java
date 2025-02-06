package ru.yandex.practicum.dto;

import java.util.Map;

public record PaginationParams(Integer pageNum, Integer pageSize) {
    public enum Param {
        PAGE_NUM("offset"),
        PAGE_SIZE("limit");

        private final String dataBaseParamName;

        Param(String dataBaseParamName) {
            this.dataBaseParamName = dataBaseParamName;
        }

        public String getDataBaseParamName() {
            return dataBaseParamName;
        }
    }

    public Map<String, Object> getQueryParams() {
        return Map.of(
                Param.PAGE_NUM.getDataBaseParamName(), pageNum,
                Param.PAGE_SIZE.getDataBaseParamName(), pageSize
        );
    }
}
