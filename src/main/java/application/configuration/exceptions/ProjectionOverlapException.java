package application.configuration.exceptions;

import application.model.ProjectionDTO;

public class ProjectionOverlapException extends Exception {

	private static final long serialVersionUID = -6843219760757203689L;

	/**
	 * Projection that is overlapping with current projection
	 */
	private ProjectionDTO projection;

	public ProjectionOverlapException() {

	}

	public ProjectionOverlapException(ProjectionDTO projection) {
		super();
		this.projection = projection;
	}

	public ProjectionOverlapException(String message) {
		super(message);
	}

	public ProjectionOverlapException(String message, ProjectionDTO projection) {
		super(message);
		this.projection = projection;
	}

	public ProjectionDTO getProjection() {
		return projection;
	}

	public void setProjection(ProjectionDTO projection) {
		this.projection = projection;
	}

}
