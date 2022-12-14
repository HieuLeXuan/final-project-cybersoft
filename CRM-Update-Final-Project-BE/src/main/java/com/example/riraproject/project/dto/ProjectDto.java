package com.example.riraproject.project.dto;

import com.example.riraproject.common.validation.annotation.FieldNotNull;
import com.example.riraproject.common.validation.annotation.UUIDConstraint;
import com.example.riraproject.common.validation.group.SaveInfo;
import com.example.riraproject.common.validation.group.UpdateInfo;
import com.example.riraproject.project.model.Project;
import com.example.riraproject.project.validation.annotation.UniqueProject;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@UniqueProject(groups = {SaveInfo.class, UpdateInfo.class})
public class ProjectDto {

    @UUIDConstraint(groups = UpdateInfo.class)
    private UUID id;

    @Size(min = 5, max = 50, message = "{project.name.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{project.name.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String name;

    @Size(min = 5, max = 500, message = "{project.description.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{project.description.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String description;

    @Size(min = 5, max = 100, message = "{project.symbol.size}", groups = {SaveInfo.class, UpdateInfo.class})
    private String symbol;

    @FieldNotNull(message = "{project.status.not-null}", groups = {SaveInfo.class, UpdateInfo.class})
    private Project.Status status;

    @Size(min = 5, max = 25, message = "{project.creatorUsername.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{project.creatorUsername.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String creatorUsername;

    @Size(min = 5, max = 25, message = "{project.leaderUsername.size}", groups = {SaveInfo.class, UpdateInfo.class})
    @NotBlank(message = "{project.leaderUsername.not-blank}", groups = {SaveInfo.class, UpdateInfo.class})
    private String leaderUsername;
}
