---
title: Pipeline BUILD — CI
date: 2026-09-12
type: best-practice
tags: [ci-cd, devsecops, build, docker, sonarqube, sbom]
projet: "[[Socle Architecture Distribuée]]"
statut: à-affiner
---

# Pipeline BUILD — CI

## 🎯 Objectif
Code source → contrôles qualité → artefact versionné → image/container → registre

## 📋 Stages

| #  | Stage                       | Objectif                                | Obligatoire |
|----|-----------------------------|-----------------------------------------|:-----------:|
| 1  | Checkout                    | Récupérer le commit exact               | ✅ |
| 2  | Workspace Cleanup           | Garantir un workspace propre            | ✅ |
| 3  | Toolchain Validation        | Vérifier Java/Maven/outils              | ✅ |
| 4  | Dependency Resolution       | Résoudre les dépendances                | ✅ |
| 5  | SCA / Dependency Scan       | Détecter CVE/dépendances vulnérables    | ✅ |
| 6  | Compile                     | Compiler le code                        | ✅ |
| 7  | Unit Tests                  | Exécuter les tests unitaires            | ✅ |
| 8  | Test Coverage               | Mesurer couverture                      | ✅ |
| 9  | Static Code Analysis        | SonarQube / qualité du code             | ✅ |
| 10 | Quality Gate                | Bloquer si qualité insuffisante         | ✅ |
| 11 | Package                     | Construire le JAR/WAR                   | ✅ |
| 12 | Integration Tests           | Tester avec les dépendances réelles     | ◾ |
| 13 | Application Security Scan   | SAST / secrets / vulnérabilités         | ◾ |
| 14 | Build Container Image       | Construire l'image OCI                  | ✅ |
| 15 | Container Image Scan        | Scanner l'image                         | ✅ |
| 16 | SBOM Generation             | Générer le SBOM                         | ✅ |
| 17 | Image Signing / Attestation | Signer/provenance de l'image            | ◾ |
| 18 | Push Artifact               | Publier JAR/image dans les registries   | ✅ |
| 19 | Publish Metadata            | Commit SHA, version, image digest, SBOM | ✅ |
| 20 | notification                | email                                   | ✅ |
## 🧩 Résultat du BUILD

```
Source
  │
  ▼
Commit SHA = abc123
  │
  ├── tests
  ├── SonarQube
  ├── SCA
  └── security
  │
  ▼
Application artifact
  │
  ▼
Container image
  │
  ▼
Image registry
  │
  ├── image: 1.4.7
  ├── digest: sha256:xxxx
  └── SBOM
  Push Image
  ↓
📧 Notification BUILD
```

## ⚠️ Point architectural important

> Le Deploy Pipeline ne doit jamais recompiler le code.
> Il doit récupérer `image@sha256:xxxx` et promouvoir exactement cette image.

## 🔗 Notes liées
- [[Socle Architecture Distribuée]]
- [[Pipeline DEPLOY — CD]]
- [[MOC-CI-CD]]
