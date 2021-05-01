package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsResponseDTO extends StatisticsDTO {
    @NotBlank
    @ApiParam(value = "Path to the current statistics", example = "countries-with-most-competitions")
    private String path;
}
