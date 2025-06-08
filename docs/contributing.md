# Contributing Guide

Thank you for your interest in contributing to the Mobile Seminar project! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

Please read and follow our [Code of Conduct](CODE_OF_CONDUCT.md) before contributing.

## Getting Started

1. Fork the repository
2. Clone your fork:
   ```bash
   git clone https://github.com/your-username/MobileSeminar.git
   ```
3. Add the original repository as upstream:
   ```bash
   git remote add upstream https://github.com/original-owner/MobileSeminar.git
   ```

## Development Workflow

1. Create a new branch for your feature or bug fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit them:
   ```bash
   git commit -m "Description of your changes"
   ```

3. Push your changes to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Create a Pull Request from your fork to the main repository.

## Coding Standards

### Java/Kotlin

- Follow the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods small and focused
- Write unit tests for new features

### Android

- Follow Material Design guidelines
- Support multiple screen sizes
- Handle configuration changes
- Implement proper error handling
- Use AndroidX libraries

### Documentation

- Update documentation for new features
- Add code comments where necessary
- Keep README.md up to date
- Document API changes

## Pull Request Process

1. Update the README.md with details of changes if needed
2. Update the documentation with any new features or changes
3. The PR will be merged once you have the sign-off of at least one other developer
4. Make sure all tests pass before submitting a PR

## Testing

- Write unit tests for new features
- Ensure all existing tests pass
- Add integration tests where appropriate
- Test on multiple Android versions

## Commit Messages

Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

Types:
- feat: A new feature
- fix: A bug fix
- docs: Documentation changes
- style: Code style changes
- refactor: Code refactoring
- test: Adding or modifying tests
- chore: Maintenance tasks

## Issue Reporting

When reporting issues, please include:

1. A clear and descriptive title
2. Steps to reproduce the issue
3. Expected behavior
4. Actual behavior
5. Screenshots if applicable
6. Environment details (OS, Android version, etc.)

## Feature Requests

When suggesting new features:

1. Use a clear and descriptive title
2. Provide a detailed description
3. Explain why this feature would be useful
4. List any similar features in other applications

## Documentation

When contributing to documentation:

1. Use clear and concise language
2. Include code examples where appropriate
3. Add screenshots for UI-related documentation
4. Keep the documentation up to date

## Review Process

1. All PRs will be reviewed by at least one maintainer
2. Reviews will focus on:
   - Code quality
   - Test coverage
   - Documentation
   - Performance
   - Security

## Getting Help

If you need help:

1. Check the documentation
2. Search existing issues
3. Ask in the project's discussion forum
4. Contact the maintainers

## License

By contributing to this project, you agree that your contributions will be licensed under the project's MIT License. 