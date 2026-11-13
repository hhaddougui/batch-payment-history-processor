# Project Roadmap

This roadmap captures proposed directions for the Batch Payment History Processor. It is not a delivery schedule; priorities and scope may change as the project evolves.

## 1. Establish Spring project conventions

- Review the applicable contribution and project-structure guidance from the [Spring Framework project](https://github.com/spring-projects/spring-framework/blob/main/CONTRIBUTING.md).
- Refactor packages and modules around clear batch, input-format, domain, and persistence responsibilities.
- Document local development, testing, configuration, and contribution conventions.
- Keep changes covered by focused tests and ensure the Maven build remains reproducible.

## 2. Add multi-format financial data ingestion

- Preserve the existing CSV workflow while introducing a common input abstraction and a normalized transaction model.
- Detect supported formats from incoming files and route them to format-specific extractors.
- Add TXT, XLSX, and PDF support, with documented format expectations and clear errors for malformed or unsupported files.
- Validate extracted records before processing and cover each format with synthetic test fixtures.
- Evaluate how to handle scanned PDFs separately from text-based PDFs before selecting an OCR approach.

## 3. Provide live batch-processing visibility

- Identify useful job and step metrics, such as status, progress, duration, and record counts.
- Evaluate Spring Batch and Spring Boot observability options for exposing metrics safely.
- Implement a dashboard or status view for monitoring active and completed batch runs.
- Ensure monitoring exposes operational metrics only, not sensitive financial record contents.

## 4. Improve contributor and AI-assistant onboarding

- Document the architecture, data flow, local setup, tests, and conventions for adding input formats.
- Provide clear contribution guidance and small, well-scoped starter tasks.
- Add or improve repository templates and automated checks that make parallel community contributions easier to review.
- Document expectations for AI-assisted contributions, including testing, privacy, and human review.
