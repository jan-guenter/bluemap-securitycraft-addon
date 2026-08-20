#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Verify the operator-supplied exact SecurityCraft runtime artifact."""

from __future__ import annotations

import argparse
import hashlib
from pathlib import Path
import zipfile


EXPECTED_SIZE = 5_193_371
EXPECTED_SHA256 = "75ac9e73c60caf58df7069f167dbacc00a640e1418207dff654f56a5fdb5f229"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--jar", type=Path, required=True)
    args = parser.parse_args()
    payload = args.jar.read_bytes()
    digest = hashlib.sha256(payload).hexdigest()
    if len(payload) != EXPECTED_SIZE or digest != EXPECTED_SHA256:
        raise SystemExit(
            f"SecurityCraft artifact mismatch: {len(payload)} bytes, SHA-256 {digest}"
        )
    with zipfile.ZipFile(args.jar) as archive:
        metadata = archive.read("META-INF/neoforge.mods.toml")
        manifest = archive.read("META-INF/MANIFEST.MF")
        if (
            b'modId="securitycraft"' not in metadata
            or b'version="${file.jarVersion}"' not in metadata
            or b"Implementation-Version: 1.10.2.1" not in manifest
        ):
            raise SystemExit("SecurityCraft metadata identity mismatch")
    print(f"Verified exact SecurityCraft artifact: {len(payload)} bytes, SHA-256 {digest}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
