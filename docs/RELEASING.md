# Release procedure

Releases are promoted only from an owner-accepted, independently audited
commit on `main`.

1. Verify the exact SecurityCraft input and run the complete Gradle, gallery,
   provenance, staged-equivalence, and artifact-byte gates used by CI.
2. Merge the required version pull request. `addon_version` must be final and
   must not end in `-SNAPSHOT`.
3. Create and push an annotated `v<addon_version>` tag at the reviewed `main`
   commit.
4. The release workflow reproduces every accepted byte, creates a draft
   prerelease, uploads and attests the assets, publishes the Maven package,
   verifies the draft assets, and only then makes the prerelease public.

Do not reuse or move a release tag. A failed prepublication run may be resumed
with the workflow's exact immutable tag input while its GitHub release remains
a draft.
